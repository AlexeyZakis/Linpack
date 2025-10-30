#include <jni.h>
#include <vector>
#include <cstdint>
#include <cmath>
#include <cstdlib>
#include <cstring>
#include <algorithm>
#include <ctime>
#include <omp.h>
#include <arm_neon.h>
#include <thread>

constexpr int BLOCK_SIZE = 64;
constexpr float EPS = 1e-7f;

static inline float frand() {
    return static_cast<float>(rand()) / static_cast<float>(RAND_MAX);
}

static inline float horizontal_sum_f32(float32x4_t v) {
#if defined(__aarch64__)
    return vaddvq_f32(v);
#else
    float32x2_t pairSum = vadd_f32(vget_low_f32(v), vget_high_f32(v));
    float32x2_t sum2 = vpadd_f32(pairSum, pairSum);
    return vget_lane_f32(sum2, 0);
#endif
}

static inline void blockGemmSub(
        const float *A,
        const float *B_T,
        float *C,
        int M, int K, int N,
        int lda, int ldb, int ldc
) {
#ifdef _OPENMP
#pragma omp parallel for collapse(2) schedule(dynamic)
#endif
    for (int i = 0; i < M; ++i) {
        for (int j = 0; j < N; ++j) {
            float32x4_t acc = vdupq_n_f32(0.f);
            int k = 0;
            for (; k + 4 <= K; k += 4) {
                float32x4_t va = vld1q_f32(A + i * lda + k);
                float32x4_t vb = vld1q_f32(B_T + j * ldb + k);
                acc = vmlaq_f32(acc, va, vb);
            }
            float sum = horizontal_sum_f32(acc);
            for (; k < K; ++k)
                sum += A[i * lda + k] * B_T[j * ldb + k];
            C[i * ldc + j] -= sum;
        }
    }
}

static inline void panel_factorize(
        float *A,
        float *b,
        int n,
        int k,
        int kb,
        std::vector<int> &pivots
) {
    pivots.resize(kb);
    for (int i = 0; i < kb; ++i) {
        int pivot = k + i;
        float maxv = std::fabs(A[(k + i) * n + k + i]);
        for (int r = k + i + 1; r < n; ++r) {
            float v = std::fabs(A[r * n + k + i]);
            if (v > maxv) {
                maxv = v;
                pivot = r;
            }
        }

        pivots[i] = pivot;

        if (pivot != k + i) {
            for (int c = k; c < n; ++c)
                std::swap(A[(k + i) * n + c], A[pivot * n + c]);
            std::swap(b[k + i], b[pivot]);
        }

        float diag = A[(k + i) * n + k + i];
        if (std::fabs(diag) < EPS) continue;

#ifdef _OPENMP
#pragma omp parallel for schedule(static)
#endif
        for (int r = k + i + 1; r < n; ++r) {
            float m = A[r * n + k + i] / diag;
            A[r * n + k + i] = m;
            for (int c = k + i + 1; c < k + kb; ++c)
                A[r * n + c] -= m * A[(k + i) * n + c];
        }
    }
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_linpack_data_GaussNative_measureGaussian(
        JNIEnv *,
        jclass,
        jint jn,
        jint jseed
) {
    const int n = static_cast<int>(jn);
    if (n <= 0) return -1;

    const auto seed = static_cast<unsigned int>(jseed);
    srand(seed);

    float *A = nullptr;
    float *b = nullptr;
    float *B_T = nullptr;

    if (posix_memalign(reinterpret_cast<void **>(&A), 64, sizeof(float) * n * n) != 0)
        return -2;
    if (posix_memalign(reinterpret_cast<void **>(&b), 64, sizeof(float) * n) != 0) {
        free(A);
        return -3;
    }
    if (posix_memalign(reinterpret_cast<void **>(&B_T), 64, sizeof(float) * n * n) != 0) {
        free(A);
        free(b);
        return -4;
    }

    unsigned int numThreads = std::thread::hardware_concurrency();
#ifdef _OPENMP
    omp_set_num_threads((int) numThreads);
#endif

#ifdef _OPENMP
#pragma omp parallel for
#endif
    for (int i = 0; i < n * n; ++i)
        A[i] = frand();

#ifdef _OPENMP
#pragma omp parallel for
#endif
    for (int i = 0; i < n; ++i)
        b[i] = frand();

    struct timespec t0{}, t1{};
    clock_gettime(CLOCK_MONOTONIC, &t0);

#ifdef _OPENMP
#pragma omp parallel for collapse(2)
#endif
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
            B_T[j * n + i] = A[i * n + j];

    const int B = BLOCK_SIZE;
    std::vector<int> pivots;
    for (int k = 0; k < n; k += B) {
        int kb = std::min(B, n - k);
        panel_factorize(A, b, n, k, kb, pivots);

        int M = n - (k + kb);
        int Nn = n - (k + kb);
        if (M > 0 && Nn > 0) {
#ifdef _OPENMP
#pragma omp parallel for collapse(2) schedule(dynamic)
#endif
            for (int ii = 0; ii < M; ii += B) {
                for (int jj = 0; jj < Nn; jj += B) {
                    int Mb = std::min(B, M - ii);
                    int Nb = std::min(B, Nn - jj);
                    float *C = A + (size_t) (k + kb + ii) * n + (k + kb + jj);
                    const float *Ablock = A + (size_t) (k + kb + ii) * n + k;
                    const float *Bblock = B_T + (k + kb + jj) * n + k;
                    blockGemmSub(Ablock, Bblock, C, Mb, kb, Nb, n, n, n);
                }
            }
        }
    }

    std::vector<float> y(n);
    for (int i = 0; i < n; ++i) {
        float s = b[i];
        for (int j = 0; j < i; ++j)
            s -= A[(size_t) i * n + j] * y[j];
        y[i] = s;
    }

    std::vector<float> x(n);
    for (int i = n - 1; i >= 0; --i) {
        float s = y[i];
        for (int j = i + 1; j < n; ++j)
            s -= A[(size_t) i * n + j] * x[j];
        float diag = A[(size_t) i * n + i];
        x[i] = (std::fabs(diag) < EPS) ? 0.0f : s / diag;
    }

    clock_gettime(CLOCK_MONOTONIC, &t1);

    const long long elapsed_ns =
            (t1.tv_sec - t0.tv_sec) * 1000000000LL +
            (t1.tv_nsec - t0.tv_nsec);

    free(A);
    free(b);
    free(B_T);

    return static_cast<jlong>(elapsed_ns);
}

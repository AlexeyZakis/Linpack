#include <jni.h>
#include <vector>
#include <cstdint>
#include <cmath>
#include <cstring>
#include <algorithm>

#ifdef _OPENMP
#include <omp.h>
#else
#warning "OpenMP not enabled!"
#endif

extern "C" {

#ifndef BLOCK_SIZE
#define BLOCK_SIZE 64
#endif

static constexpr float EPS = 1e-12f;

static inline void blockGemmSub(
    const float* __restrict__ A,
    const float* __restrict__ B,
    float* __restrict__ C,
    int M,
    int K,
    int N,
    int lda,
    int ldb,
    int ldc
) {
    for (int i = 0; i < M; ++i) {
        const float* Ai = A + i * lda;
        float* Ci = C + i * ldc;
        for (int k = 0; k < K; ++k) {
            float a_ik = Ai[k];
            if (fabsf(a_ik) < EPS) continue;
            for (int j = 0; j < N; ++j) {
                Ci[j] -= a_ik * B[k * ldb + j];
            }
        }
    }
}

static inline void swap_rows(float* A, int r1, int r2, int nСols) {
    if (r1 == r2) return;
    float* a1 = A + r1 * nСols;
    float* a2 = A + r2 * nСols;
    for (int j = 0; j < nСols; ++j) std::swap(a1[j], a2[j]);
}

static void panel_factorize(
    float* A,
    float* b,
    int n,
    int panel_start,
    int bs,
    std::vector<int>& piv
) {
    int end = std::min(n, panel_start + bs);
    piv.resize(end - panel_start);
    for (int k = panel_start; k < end; ++k) {
        int piv_row = k;
        float maxV = fabsf(A[k * n + k]);
        for (int i = k + 1; i < n; ++i) {
            float v = fabsf(A[i * n + k]);
            if (v > maxV) { maxV = v; piv_row = i; }
        }
        piv[k - panel_start] = piv_row;
        if (piv_row != k) {
            swap_rows(A, k, piv_row, n);
            std::swap(b[k], b[piv_row]);
        }
        float akk = A[k * n + k];
        if (fabsf(akk) < EPS) continue;
        float inv_akk = 1.0f / akk;
        float* Ak = A + k * n;
        for (int i = k + 1; i < n; ++i) {
            float* Ai = A + i * n;
            float factor = Ai[k] * inv_akk;
            Ai[k] = 0.0f;
            for (int j = k + 1; j < n; ++j) Ai[j] -= factor * Ak[j];
            b[i] -= factor * b[k];
        }
    }
}

JNIEXPORT jfloatArray JNICALL
Java_com_example_linpack_data_GaussNative_solveGaussian(
    JNIEnv* env,
    jclass,
    jint jn,
    jfloatArray jA,
    jfloatArray jb
) {
    int n = (int) jn;
    if (n <= 0) return nullptr;

    jboolean aCopy, bCopy;
    jfloat* aData = env->GetFloatArrayElements(jA, &aCopy);
    jfloat* bData = env->GetFloatArrayElements(jb, &bCopy);
    if (!aData || !bData) {
        if (aData) env->ReleaseFloatArrayElements(jA, aData, 0);
        if (bData) env->ReleaseFloatArrayElements(jb, bData, 0);
        return nullptr;
    }

    std::vector<float> A_local((size_t)n * n);
    std::vector<float> b_local(n);
    std::memcpy(A_local.data(), aData, sizeof(float) * n * n);
    std::memcpy(b_local.data(), bData, sizeof(float) * n);

    env->ReleaseFloatArrayElements(jA, aData, JNI_ABORT);
    env->ReleaseFloatArrayElements(jb, bData, JNI_ABORT);

    std::vector<int> pivots;

    const int B = BLOCK_SIZE;
    for (int k = 0; k < n; k += B) {
        int kb = std::min(B, n - k);
        panel_factorize(A_local.data(), b_local.data(), n, k, kb, pivots);

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
                    float* C = A_local.data() + (k + kb + ii) * n + (k + kb + jj);
                    const float* Ablock = A_local.data() + (k + kb + ii) * n + k;
                    const float* Bblock = A_local.data() + k * n + (k + kb + jj);
                    blockGemmSub(Ablock, Bblock, C, Mb, kb, Nb, n, n, n);
                }
            }
        }
    }

    std::vector<float> y(n);
    for (int i = 0; i < n; ++i) {
        float s = b_local[i];
        for (int j = 0; j < i; ++j) s -= A_local[i * n + j] * y[j];
        y[i] = s;
    }

    std::vector<float> x(n);
    for (int i = n - 1; i >= 0; --i) {
        float s = y[i];
        for (int j = i + 1; j < n; ++j) s -= A_local[i * n + j] * x[j];
        float uii = A_local[i * n + i];
        x[i] = (fabsf(uii) < EPS) ? 0.0f : s / uii;
    }

    jfloatArray jx = env->NewFloatArray(n);
    env->SetFloatArrayRegion(jx, 0, n, x.data());
    return jx;
}

} // extern "C"

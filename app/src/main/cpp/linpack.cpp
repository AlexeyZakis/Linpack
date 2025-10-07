#include <jni.h>
#include <vector>
#include <cstdint>
#include <cmath>
#include <cstring>
#include <algorithm>

#ifdef __ARM_NEON
#include <arm_neon.h>
#endif

#ifdef _OPENMP
#include <omp.h>
#else
#warning "OpenMP not enabled!"
#endif

extern "C" {

#ifndef BLOCK_SIZE
#define BLOCK_SIZE 64
#endif

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
        const float* Ai = A + (size_t)i * lda;
        float* Ci = C + (size_t)i * ldc;
        for (int k = 0; k < K; ++k) {
            float a_ik = Ai[k];
            if (a_ik == 0.0f) continue;
#ifdef __ARM_NEON
            float32x4_t a_vec = vdupq_n_f32(a_ik);
            int j = 0;
            for (; j + 4 <= N; j += 4) {
                float32x4_t c_vec = vld1q_f32(Ci + j);
                float32x4_t b_vec = vld1q_f32(B + (size_t)k * ldb + j);
                c_vec = vmlsq_f32(c_vec, a_vec, b_vec);
                vst1q_f32(Ci + j, c_vec);
            }
            for (; j < N; ++j) {
                Ci[j] -= a_ik * B[(size_t)k * ldb + j];
            }
#else
            for (int j = 0; j < N; ++j) {
                Ci[j] -= a_ik * B[(size_t)k * ldb + j];
            }
#endif
        }
    }
}

static inline void swap_rows(float* A, int r1, int r2, int ncols) {
    if (r1 == r2) return;
    float* a1 = A + (size_t)r1 * ncols;
    float* a2 = A + (size_t)r2 * ncols;
    for (int j = 0; j < ncols; ++j) std::swap(a1[j], a2[j]);
}

static void panel_factorize(float* A, float* b, int n, int panel_start, int bs, std::vector<int>& piv) {
    int end = std::min(n, panel_start + bs);
    for (int k = panel_start; k < end; ++k) {
        int piv_row = k;
        float maxV = fabsf(A[(size_t)k * n + k]);
        for (int i = k + 1; i < n; ++i) {
            float v = fabsf(A[(size_t)i * n + k]);
            if (v > maxV) { maxV = v; piv_row = i; }
        }
        piv.push_back(piv_row);
        if (piv_row != k) {
            swap_rows(A, k, piv_row, n);
            std::swap(b[k], b[piv_row]);
        }
        float akk = A[(size_t)k * n + k];
        if (fabsf(akk) < 1e-12f) {
            continue;
        }
        float inv_akk = 1.0f / akk;
        float* Ak = A + (size_t)k * n;
        for (int i = k + 1; i < n; ++i) {
            float* Ai = A + (size_t)i * n;
            float factor = Ai[k] * inv_akk;
            Ai[k] = 0.0f;
#ifdef __ARM_NEON
            int j = k + 1;
            float32x4_t fvec = vdupq_n_f32(factor);
            for (; j + 4 <= n; j += 4) {
                float32x4_t vAi = vld1q_f32(Ai + j);
                float32x4_t vAk = vld1q_f32(Ak + j);
                vAi = vmlsq_f32(vAi, fvec, vAk);
                vst1q_f32(Ai + j, vAi);
            }
            for (; j < n; ++j) Ai[j] -= factor * Ak[j];
#else
            for (int j = k + 1; j < n; ++j) Ai[j] -= factor * Ak[j];
#endif
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
    jboolean aCopy = JNI_FALSE;
    jfloat* aData = (jfloat*) env->GetPrimitiveArrayCritical(jA, &aCopy);
    jboolean bCopy = JNI_FALSE;
    jfloat* bData = (jfloat*) env->GetPrimitiveArrayCritical(jb, &bCopy);
    if (!aData || !bData) {
        if (aData) env->ReleasePrimitiveArrayCritical(jA, aData, 0);
        if (bData) env->ReleasePrimitiveArrayCritical(jb, bData, 0);
        return nullptr;
    }

    std::vector<float> A_local((size_t)n * n);
    std::vector<float> b_local(n);
    memcpy(A_local.data(), aData, sizeof(float) * (size_t)n * n);
    memcpy(b_local.data(), bData, sizeof(float) * n);

    env->ReleasePrimitiveArrayCritical(jA, aData, JNI_ABORT);
    env->ReleasePrimitiveArrayCritical(jb, bData, JNI_ABORT);

    const int B = BLOCK_SIZE;
    for (int k = 0; k < n; k += B) {
        int kb = std::min(B, n - k);
        std::vector<int> piv;
        panel_factorize(A_local.data(), b_local.data(), n, k, kb, piv);
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
                    float* C = A_local.data() + (size_t)(k + kb + ii) * n + (k + kb + jj);
                    const float* Ablock = A_local.data() + (size_t)(k + kb + ii) * n + k;
                    const float* Bblock = A_local.data() + (size_t)k * n + (k + kb + jj);
                    blockGemmSub(Ablock, Bblock, C, Mb, kb, Nb, n, n, n);
                }
            }
        }
    }

    std::vector<float> y(n);
    for (int i = 0; i < n; ++i) {
        float s = b_local[i];
        for (int j = 0; j < i; ++j) s -= A_local[(size_t)i * n + j] * y[j];
        y[i] = s;
    }
    std::vector<float> x(n);
    for (int i = n - 1; i >= 0; --i) {
        float s = y[i];
        for (int j = i + 1; j < n; ++j) s -= A_local[(size_t)i * n + j] * x[j];
        float uii = A_local[(size_t)i * n + i];
        if (fabsf(uii) < 1e-12f) x[i] = 0.0f;
        else x[i] = s / uii;
    }

    jfloatArray jx = env->NewFloatArray(n);
    env->SetFloatArrayRegion(jx, 0, n, x.data());
    return jx;
}

} // extern "C"

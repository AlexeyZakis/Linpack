package com.example.linpack.data

import com.example.linpack.domain.LinpackManager
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.domain.models.LinpackResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext
import kotlin.math.min
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class LinpackManagerImpl : LinpackManager {
    private var job: Deferred<LinpackResult>? = null

    override suspend fun checkDevicePerformance(
        matrixSize: Int,
        cores: Int,
        gaussImpl: GaussImpl,
    ): LinpackResult {
        job?.takeIf { it.isActive }?.cancelAndJoin()

        val deferred = CoroutineScope(Dispatchers.Default).async {
            val b = FloatArray(matrixSize) { Random.nextFloat() }

            val elapsed = measureTimeMillis {
                when (gaussImpl) {
                    GaussImpl.Cpp -> {
                        val a = FloatArray(matrixSize * matrixSize) { Random.nextFloat() }
                        GaussNative.solveGaussian(matrixSize, a.copyOf(), b.copyOf())
                    }

                    GaussImpl.Kotlin -> {
                        val a = Array(matrixSize) { FloatArray(matrixSize) { Random.nextFloat() } }
                        val x = FloatArray(matrixSize)
                        gaussianElimination(
                            A = a,
                            b = b,
                            x = x,
                            cores = cores,
                        )
                    }
                }
            }

            val estimatedNumOfOperations = matrixSize.matrixSizeToEstimatedNumOfOperations()
            val durationSec = elapsed.msToSec()
            val flops = estimatedNumOfOperations / durationSec
            val mFlops = flops.flopsToMFlops()

            LinpackResult(
                mFlops = mFlops,
                durationSec = durationSec,
                cores = cores,
                matrixSize = matrixSize,
                gaussImpl = gaussImpl,
            )
        }

        job = deferred

        return try {
            deferred.await()
        } catch (e: CancellationException) {
            LinpackResult()
        }
    }

    private suspend fun gaussianElimination(
        A: Array<FloatArray>,
        b: FloatArray,
        x: FloatArray,
        cores: Int,
    ) {
        val n = A.size

        for (k in 0 until n) {
            coroutineContext.ensureActive()
            val pivot = A[k][k]

            for (j in k until n) {
                coroutineContext.ensureActive()
                A[k][j] /= pivot
            }
            b[k] /= pivot

            val rowsPerBlock = ((n - k - 1) + cores - 1) / cores
            coroutineScope {
                (0 until cores).map { core ->
                    async(Dispatchers.Default) {
                        val start = k + 1 + core * rowsPerBlock
                        val end = min(k + 1 + (core + 1) * rowsPerBlock, n)
                        for (i in start until end) {
                            coroutineContext.ensureActive()
                            val factor = A[i][k]
                            for (j in k until n) {
                                coroutineContext.ensureActive()
                                A[i][j] -= factor * A[k][j]
                            }
                            b[i] -= factor * b[k]
                        }
                    }
                }.awaitAll()
            }
        }

        for (i in n - 1 downTo 0) {
            coroutineContext.ensureActive()
            x[i] = b[i]
            for (j in i + 1 until n) {
                coroutineContext.ensureActive()
                x[i] -= A[i][j] * x[j]
            }
        }
    }
}

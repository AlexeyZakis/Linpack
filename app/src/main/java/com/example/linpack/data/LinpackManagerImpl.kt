package com.example.linpack.data

import com.example.linpack.domain.LinpackManager
import com.example.linpack.domain.models.LinpackResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext
import kotlin.math.min
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class LinpackManagerImpl : LinpackManager {
    private var job: Deferred<LinpackResult>? = null

    private val _progress = MutableStateFlow(0)
    override val progress = _progress.asStateFlow()

    override suspend fun cancelCalculation() {
        job?.cancelAndJoin()
        job = null
        _progress.value = 0
    }

    override suspend fun getDeviceMFlops(
        matrixSize: Int,
        cores: Int,
    ): LinpackResult {
        job?.takeIf { it.isActive }?.cancelAndJoin()

        _progress.value = 0

        val deferred = CoroutineScope(Dispatchers.Default).async {
            val A = Array(matrixSize) { DoubleArray(matrixSize) { Random.nextDouble() } }
            val b = DoubleArray(matrixSize) { Random.nextDouble() }
            val x = DoubleArray(matrixSize)

            val elapsed = measureTimeMillis {
                gaussianElimination(
                    A = A,
                    b = b,
                    x = x,
                    cores = cores,
                )
            }
            val estimatedNumOfOperations = matrixSize.matrixSizeToEstimatedNumOfOperations()
            val durationSec = elapsed.msToSec()
            val flops = estimatedNumOfOperations / durationSec
            val mFlops = flops.flopsToMFlops()

            _progress.value = 100

            LinpackResult(
                mFlops = mFlops,
                durationSec = durationSec,
            )
        }

        job = deferred

        return try {
            deferred.await()
        } catch (e: CancellationException) {
            LinpackResult(
                mFlops = 0.0,
                durationSec = 0.0,
                cancelled = true,
            )
        }
    }

    private suspend fun gaussianElimination(
        A: Array<DoubleArray>,
        b: DoubleArray,
        x: DoubleArray,
        cores: Int,
    ) {
        val n = A.size

        val totalOps = (2.0 / 3.0 * n * n * n).toLong()
        var doneOps = 0L

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

            val opsThisStep = 2L * (n - k - 1) * (n - k)
            doneOps += opsThisStep

            val progressPercent = ((doneOps.toDouble() / totalOps) * 100).toInt()
            _progress.value = min(progressPercent, 100)
        }

        for (i in n - 1 downTo 0) {
            coroutineContext.ensureActive()
            x[i] = b[i]
            for (j in i + 1 until n) {
                coroutineContext.ensureActive()
                x[i] -= A[i][j] * x[j]
            }
        }
        _progress.value = 100
    }
}

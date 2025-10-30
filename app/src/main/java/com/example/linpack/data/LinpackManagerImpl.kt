package com.example.linpack.data

import com.example.linpack.domain.LinpackManager
import com.example.linpack.domain.models.LinpackResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.cancellation.CancellationException

class LinpackManagerImpl : LinpackManager {
    private val _currentRunNumber = MutableStateFlow(0)
    override val currentRunNumber = _currentRunNumber.asStateFlow()

    private var job: Deferred<LinpackResult>? = null

    override suspend fun checkDevicePerformance(
        matrixSize: Int,
        numOfRuns: Int,
        seed: Int,
    ): LinpackResult {
        val linpackResults = mutableSetOf<LinpackResult>()
        _currentRunNumber.value = 0

        repeat(Constants.WARMUP_RUNS) {
            GaussNative.measureGaussian(
                matrixSize = matrixSize,
                seed = seed,
            )
        }

        repeat(numOfRuns) {
            _currentRunNumber.update { currentRun ->
                currentRun + 1
            }
            val linpackResult = linpackRun(
                matrixSize = matrixSize,
                seed = seed,
                numOfRuns = numOfRuns,
            )
            linpackResults.add(linpackResult)
        }
        val linpackAvgResult = countAvgLinpackResult(linpackResults)
        return linpackAvgResult
    }

    private suspend fun linpackRun(
        matrixSize: Int,
        seed: Int,
        numOfRuns: Int,
    ): LinpackResult {
        val deferred = CoroutineScope(Dispatchers.Default).async {
            val elapsed = GaussNative.measureGaussian(
                matrixSize = matrixSize,
                seed = seed,
            )

            val estimatedNumOfOperations = matrixSize.matrixSizeToEstimatedNumOfOperations()
            val durationSec = elapsed.nsToSec()
            val flops = estimatedNumOfOperations / durationSec
            val mFlops = flops.flopsToMFlops()

            LinpackResult.Success(
                mFlops = mFlops,
                durationSec = durationSec,
                seed = seed,
                matrixSize = matrixSize,
                numOfRuns = numOfRuns,
            )
        }

        job = deferred

        return try {
            deferred.await()
        } catch (_: CancellationException) {
            LinpackResult.Error
        }
    }

    private fun countAvgLinpackResult(linpackResults: Set<LinpackResult>): LinpackResult {
        if (linpackResults.any { it is LinpackResult.Error }) {
            return LinpackResult.Error
        }
        val linpackResults = linpackResults.map { it as LinpackResult.Success }

        val matrixSize = linpackResults.first().matrixSize
        val seed = linpackResults.first().seed
        val numOfRuns = linpackResults.first().numOfRuns

        var durationAvg = 0.0
        var mFlopsAvg = 0.0
        linpackResults.forEach { linpackResult ->
            durationAvg += linpackResult.durationSec
            mFlopsAvg += linpackResult.mFlops
        }
        durationAvg /= linpackResults.size
        mFlopsAvg /= linpackResults.size

        val linpackResult = LinpackResult.Success(
            mFlops = mFlopsAvg,
            durationSec = durationAvg,
            matrixSize = matrixSize,
            seed = seed,
            numOfRuns = numOfRuns,
        )
        return linpackResult
    }
}

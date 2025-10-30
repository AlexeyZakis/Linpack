package com.example.linpack.domain.models

import com.example.linpack.data.Constants

sealed class LinpackResult {
    data class Success(
        val mFlops: Double = 0.0,
        val durationSec: Double = 0.0,
        val seed: Int = Constants.SEED,
        val matrixSize: Int = Constants.MATRIX_SIZE_DEFAULT,
        val numOfRuns: Int = Constants.NUM_OF_RUNS_DEFAULT,
    ) : LinpackResult()

    object Error : LinpackResult()
}



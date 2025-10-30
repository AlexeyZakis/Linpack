package com.example.linpack.presentation.screens.linpack

import com.example.linpack.data.Constants
import com.example.linpack.domain.models.LinpackResult

data class LinpackScreenState(
    val linpackDone: Boolean = false,
    val inProgress: Boolean = false,
    val cores: Int = 0,
    val matrixSize: Int = Constants.MATRIX_SIZE_DEFAULT,
    val matrixSizeInProgress: Int = 0,
    val numOfRuns: Int = Constants.NUM_OF_RUNS_DEFAULT,
    val numOfRunsInProgress: Int = 0,
    val currentRunNumber: Int = 0,
    val linpackResult: LinpackResult = LinpackResult.Success(),
    val estimatedCpuMFlops: Int = 0,
)

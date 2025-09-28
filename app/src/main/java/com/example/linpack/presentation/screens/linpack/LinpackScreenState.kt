package com.example.linpack.presentation.screens.linpack

import com.example.linpack.data.Constants

data class LinpackScreenState(
    val inProgress: Boolean = false,
    val isCancelling: Boolean = false,
    val cores: Int = 0,
    val availableMemoryMB: Int = 0,
    val matrixSize: Int = Constants.MATRIX_SIZE_DEFAULT,
    val durationSec: Double = 0.0,
    val mFlops: Double = 0.0,
    val coresInProgress: Int = 0,
    val matrixSizeInProgress: Int = 0,
    val maxCores: Int = 0,
    val progress: Int = 0,
)

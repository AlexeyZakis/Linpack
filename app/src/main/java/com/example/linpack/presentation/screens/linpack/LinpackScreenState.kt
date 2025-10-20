package com.example.linpack.presentation.screens.linpack

import com.example.linpack.data.Constants
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.domain.models.LinpackResult

data class LinpackScreenState(
    val linpackDone: Boolean = false,
    val inProgress: Boolean = false,
    val cores: Int = 0,
    val requiredMemoryMB: Int = 0,
    val availableMemoryMB: Int = 0,
    val matrixSize: Int = Constants.MATRIX_SIZE_DEFAULT,
    val matrixSizeInProgress: Int = 0,
    val gaussImpl: GaussImpl = GaussImpl.DEFAULT,
    val gaussImplInProgress: GaussImpl = GaussImpl.DEFAULT,
    val linpackResult: LinpackResult = LinpackResult(),
    val enoughMemory: Boolean = true,
)

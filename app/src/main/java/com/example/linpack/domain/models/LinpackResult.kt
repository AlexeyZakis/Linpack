package com.example.linpack.domain.models

import com.example.linpack.data.Constants

data class LinpackResult(
    val mFlops: Double = 0.0,
    val durationSec: Double = 0.0,
    val cores: Int = 0,
    val matrixSize: Int = Constants.MATRIX_SIZE_DEFAULT,
    val gaussImpl: GaussImpl = GaussImpl.DEFAULT,
)

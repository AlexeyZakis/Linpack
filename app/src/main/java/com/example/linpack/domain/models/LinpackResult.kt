package com.example.linpack.domain.models

data class LinpackResult(
    val mFlops: Double = 0.0,
    val durationSec: Double = 0.0,
    val cancelled: Boolean = false,
)

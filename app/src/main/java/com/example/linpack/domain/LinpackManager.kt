package com.example.linpack.domain

import com.example.linpack.domain.models.LinpackResult
import kotlinx.coroutines.flow.StateFlow

interface LinpackManager {
    val currentRunNumber: StateFlow<Int>

    suspend fun checkDevicePerformance(
        matrixSize: Int,
        numOfRuns: Int,
        seed: Int,
    ): LinpackResult
}

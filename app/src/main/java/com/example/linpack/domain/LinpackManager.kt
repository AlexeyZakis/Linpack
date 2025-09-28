package com.example.linpack.domain

import com.example.linpack.domain.models.LinpackResult
import kotlinx.coroutines.flow.StateFlow

interface LinpackManager {
    val progress: StateFlow<Int>

    suspend fun getDeviceMFlops(matrixSize: Int, cores: Int): LinpackResult
    suspend fun cancelCalculation()
}

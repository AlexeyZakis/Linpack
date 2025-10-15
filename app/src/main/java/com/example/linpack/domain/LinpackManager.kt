package com.example.linpack.domain

import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.domain.models.LinpackResult

interface LinpackManager {
    suspend fun checkDevicePerformance(matrixSize: Int, cores: Int, gaussImpl: GaussImpl): LinpackResult
}

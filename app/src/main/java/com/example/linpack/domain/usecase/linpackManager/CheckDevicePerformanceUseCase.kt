package com.example.linpack.domain.usecase.linpackManager

import com.example.linpack.domain.LinpackManager
import com.example.linpack.domain.models.GaussImpl

class CheckDevicePerformanceUseCase(
    private val linpackManager: LinpackManager,
) {
    suspend operator fun invoke(
        matrixSize: Int,
        cores: Int,
        gaussImpl: GaussImpl,
    ) = linpackManager.checkDevicePerformance(
        matrixSize = matrixSize,
        cores = cores,
        gaussImpl = gaussImpl,
    )
}

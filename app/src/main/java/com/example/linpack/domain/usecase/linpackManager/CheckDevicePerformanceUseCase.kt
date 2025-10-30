package com.example.linpack.domain.usecase.linpackManager

import com.example.linpack.domain.LinpackManager

class CheckDevicePerformanceUseCase(
    private val linpackManager: LinpackManager,
) {
    suspend operator fun invoke(
        matrixSize: Int,
        numOfRuns: Int,
        seed: Int,
    ) = linpackManager.checkDevicePerformance(
        matrixSize = matrixSize,
        numOfRuns = numOfRuns,
        seed = seed,
    )
}

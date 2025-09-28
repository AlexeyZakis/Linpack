package com.example.linpack.domain.usecase.deviceResourcesAnalyzer

import com.example.linpack.domain.DeviceResourcesAnalyzer

class CountAvailableMemoryUseCase(
    private val deviceResourcesAnalyzer: DeviceResourcesAnalyzer,
) {
    operator fun invoke(matrixSize: Int) =
        deviceResourcesAnalyzer.countAvailableMemory(matrixSize = matrixSize)
}

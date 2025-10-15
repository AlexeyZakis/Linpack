package com.example.linpack.domain.usecase.deviceResourcesAnalyzer

import com.example.linpack.domain.DeviceResourcesAnalyzer

class CountRequiredMemoryMBUseCase(
    private val deviceResourcesAnalyzer: DeviceResourcesAnalyzer,
) {
    operator fun invoke(matrixSize: Int) =
        deviceResourcesAnalyzer.countRequiredMemoryMB(matrixSize = matrixSize)
}

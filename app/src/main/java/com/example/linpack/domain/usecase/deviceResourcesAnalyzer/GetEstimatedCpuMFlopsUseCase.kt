package com.example.linpack.domain.usecase.deviceResourcesAnalyzer

import com.example.linpack.domain.DeviceResourcesAnalyzer

class GetEstimatedCpuMFlopsUseCase(
    private val deviceResourcesAnalyzer: DeviceResourcesAnalyzer,
) {
    operator fun invoke() =
        deviceResourcesAnalyzer.getEstimateCpuMFlops()
}

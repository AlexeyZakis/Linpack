package com.example.linpack.presentation.di.usecases.managers

import com.example.linpack.domain.DeviceResourcesAnalyzer
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetDeviceResourcesUseCase
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetEstimatedCpuMFlopsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DeviceResourcesAnalyzerModule {
    @Provides
    fun provideGetDeviceResourcesUseCase(deviceResourcesAnalyzer: DeviceResourcesAnalyzer) =
        GetDeviceResourcesUseCase(deviceResourcesAnalyzer = deviceResourcesAnalyzer)

    @Provides
    fun provideGetEstimatedCpuMFlopsUseCase(deviceResourcesAnalyzer: DeviceResourcesAnalyzer) =
        GetEstimatedCpuMFlopsUseCase(deviceResourcesAnalyzer = deviceResourcesAnalyzer)
}

package com.example.linpack.presentation.di.usecases.managers

import com.example.linpack.domain.DeviceResourcesAnalyzer
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.CountRequiredMemoryMBUseCase
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetAvailableMemoryMBUseCase
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetDeviceResourcesUseCase
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
    fun provideCountRequiredMemoryMBUseCase(deviceResourcesAnalyzer: DeviceResourcesAnalyzer) =
        CountRequiredMemoryMBUseCase(deviceResourcesAnalyzer = deviceResourcesAnalyzer)

    @Provides
    fun provideGetAvailableMemoryMBUseCase(deviceResourcesAnalyzer: DeviceResourcesAnalyzer) =
        GetAvailableMemoryMBUseCase(deviceResourcesAnalyzer = deviceResourcesAnalyzer)
}

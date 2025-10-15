package com.example.linpack.presentation.di.usecases.managers

import com.example.linpack.domain.LinpackManager
import com.example.linpack.domain.usecase.linpackManager.CheckDevicePerformanceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LinpackManagerModule {
    @Provides
    fun provideCheckDevicePerformanceUseCase(linpackManager: LinpackManager) =
        CheckDevicePerformanceUseCase(linpackManager = linpackManager)
}

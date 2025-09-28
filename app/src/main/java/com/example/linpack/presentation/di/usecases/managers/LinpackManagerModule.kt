package com.example.linpack.presentation.di.usecases.managers

import com.example.linpack.domain.LinpackManager
import com.example.linpack.domain.usecase.linpackManager.CancelCalculationUseCase
import com.example.linpack.domain.usecase.linpackManager.GetDeviceMFlopsUseCase
import com.example.linpack.domain.usecase.linpackManager.GetProgressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LinpackManagerModule {
    @Provides
    fun provideGetDeviceMFlopsUseCase(linpackManager: LinpackManager) =
        GetDeviceMFlopsUseCase(linpackManager = linpackManager)

    @Provides
    fun provideGetProgressUseCase(linpackManager: LinpackManager) =
        GetProgressUseCase(linpackManager = linpackManager)

    @Provides
    fun provideCancelCalculationUseCase(linpackManager: LinpackManager) =
        CancelCalculationUseCase(linpackManager = linpackManager)
}

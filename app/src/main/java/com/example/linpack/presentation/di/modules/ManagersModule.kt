package com.example.linpack.presentation.di.modules

import com.example.linpack.data.DeviceResourcesAnalyzerImpl
import com.example.linpack.data.LinpackManagerImpl
import com.example.linpack.domain.DeviceResourcesAnalyzer
import com.example.linpack.domain.LinpackManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagersModule {
    @Provides
    @Singleton
    fun provideLinpackManager(): LinpackManager =
        LinpackManagerImpl()

    @Provides
    @Singleton
    fun provideDeviceResourcesAnalyzer(): DeviceResourcesAnalyzer =
        DeviceResourcesAnalyzerImpl()
}

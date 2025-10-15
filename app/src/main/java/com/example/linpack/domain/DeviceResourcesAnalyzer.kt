package com.example.linpack.domain

import com.example.linpack.domain.models.CpuFrequency
import com.example.linpack.domain.models.DeviceResources

interface DeviceResourcesAnalyzer {
    fun getDeviceResources(): DeviceResources
    fun getCpuFrequencies(): Map<Int, CpuFrequency>

    fun countRequiredMemoryMB(matrixSize: Int): Double
}

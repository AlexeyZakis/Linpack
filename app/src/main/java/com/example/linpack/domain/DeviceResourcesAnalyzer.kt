package com.example.linpack.domain

import com.example.linpack.domain.models.CpuFrequency
import com.example.linpack.domain.models.DeviceResources

interface DeviceResourcesAnalyzer {
    fun getDeviceResources(): DeviceResources
    fun getCpuFrequencies(): Map<Int, CpuFrequency>

    fun countAvailableMemory(matrixSize: Int): Double
}

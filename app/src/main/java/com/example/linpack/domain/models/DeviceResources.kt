package com.example.linpack.domain.models

data class DeviceResources(
    val cores: Int = 0,
    val maxMemoryMB: Double = 0.0,
    val usedMemoryMB: Double = 0.0,
)

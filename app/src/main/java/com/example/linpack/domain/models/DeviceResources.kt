package com.example.linpack.domain.models

data class DeviceResources(
    val cores: Int = 0,
    val maxMemory: Double = 0.0,
    val usedMemory: Double = 0.0,
)

package com.example.linpack.data

import com.example.linpack.domain.DeviceResourcesAnalyzer
import com.example.linpack.domain.models.CpuFrequency
import com.example.linpack.domain.models.DeviceResources
import java.io.File

class DeviceResourcesAnalyzerImpl : DeviceResourcesAnalyzer {
    override fun getDeviceResources(): DeviceResources {
        val cores = Runtime.getRuntime().availableProcessors()
        val maxMemoryMB = Runtime.getRuntime().maxMemory().byteToMB()
        val usedMemoryMB = (Runtime.getRuntime().totalMemory()
                - Runtime.getRuntime().freeMemory()).byteToMB()

        val deviceResources = DeviceResources(
            cores = cores,
            maxMemoryMB = maxMemoryMB,
            usedMemoryMB = usedMemoryMB,
        )
        return deviceResources
    }

    override fun getEstimateCpuMFlops(): Int {
        val cpuFrequencies = getCpuFrequencies()
        val cpuMFlops = cpuFrequencies.map {
            it.value
        }.sumOf {
            it.maxMHz
        } * Constants.HEURISTICALLY_FLOPS_PER_CYCLE
        return cpuMFlops
    }

    override fun getCpuFrequencies(): Map<Int, CpuFrequency> {
        val result = mutableMapOf<Int, CpuFrequency>()
        val cpuCount = Runtime.getRuntime().availableProcessors()
        for (i in 0 until cpuCount) {
            try {
                val minFreqFile = File("/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_min_freq")
                val maxFreqFile = File("/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_max_freq")

                if (minFreqFile.exists() && maxFreqFile.exists()) {
                    val minFreqMHz = minFreqFile.readText().trim().toInt().kHzToMHz()
                    val maxFreqMHz = maxFreqFile.readText().trim().toInt().kHzToMHz()
                    val cpuFrequency = CpuFrequency(
                        minMHz = minFreqMHz,
                        maxMHz = maxFreqMHz,
                    )
                    result[i] = cpuFrequency
                }
            } catch (e: Exception) {

            }
        }
        return result
    }
}

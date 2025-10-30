package com.example.linpack.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.linpack.R
import com.example.linpack.domain.models.LinpackResult

@Composable
fun toResultString(
    result: LinpackResult.Success,
    estimatedCpuMFlops: Int,
    cores: Int,
): String {
    val flops = getStringWithLabel(
        labelResId = R.string.avgFlops,
        value = mFlopsToGFlops(result.mFlops),
    )
    val roundedDurationSec = durationToRoundedString(result.durationSec)
    val estimatedCpuMFlopsFormated = mFlopsToGFlops(estimatedCpuMFlops.toDouble())

    val duration = getStringWithLabel(
        labelResId = R.string.avgDuration,
        unitOfMeasurementResId = R.string.seconds,
        value = roundedDurationSec,
    )
    val cores = getStringWithLabel(
        labelResId = R.string.cores,
        value = "$cores",
    )
    val matrixSize = getStringWithLabel(
        labelResId = R.string.matrixSize,
        value = "${result.matrixSize}",
    )
    val numOfRuns = getStringWithLabel(
        labelResId = R.string.numOfRuns,
        value = "${result.numOfRuns}",
    )
    val estimatedCpuMFlopsStr = getStringWithLabel(
        labelResId = R.string.estimatedCpuFlops,
        value = estimatedCpuMFlopsFormated,
    )
    var result = flops +
            "\n$duration" +
            "\n$cores" +
            "\n$matrixSize" +
            "\n$numOfRuns"

    if (estimatedCpuMFlops != 0) {
        result = "$result\n$estimatedCpuMFlopsStr"
    }
    return result
}

@Composable
private fun getStringWithLabel(
    labelResId: Int,
    value: String,
    unitOfMeasurementResId: Int? = null,
): String {
    val label = stringResource(labelResId)
    val unitOfMeasurement = unitOfMeasurementResId?.let { stringResource(it) } ?: ""
    val result = "$label: $value$unitOfMeasurement"
    return result
}

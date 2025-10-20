package com.example.linpack.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.linpack.R
import com.example.linpack.domain.models.LinpackResult

@Composable
fun LinpackResult.toResultString(): String {
    val flops = getStringWithLabel(
        labelResId = R.string.linpackFlops,
        value = mFlopsToGFlops(mFlops),
    )
    val roundedDurationSec = "%.2f".format(durationSec)
    val duration = getStringWithLabel(
        labelResId = R.string.linpackDuration,
        unitOfMeasurementResId = R.string.seconds,
        value = roundedDurationSec,
    )
    val cores = getStringWithLabel(
        labelResId = R.string.cores,
        value = "$cores",
    )
    val matrixSize = getStringWithLabel(
        labelResId = R.string.matrixSize,
        value = "$matrixSize",
    )
    val gaussImplementation = getStringWithLabel(
        labelResId = R.string.gaussImplementation,
        value = stringResource(gaussImpl.toResId()),
    )
    val result = "$flops\n" +
            "$duration\n" +
            "$cores\n" +
            "$matrixSize\n" +
            "$gaussImplementation"
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

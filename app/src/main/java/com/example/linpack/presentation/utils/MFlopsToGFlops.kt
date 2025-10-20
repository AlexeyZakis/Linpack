package com.example.linpack.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.linpack.R

@Composable
fun mFlopsToGFlops(
    value: Double,
): String {
    val div1000 = value / 1000
    val result = if (div1000 >= 1) {
        val roundedFlops = "%.2f".format(div1000)
        val unit = stringResource(R.string.gFlops)
        "$roundedFlops$unit"
    } else {
        val roundedFlops = "%.2f".format(value)
        val unit = stringResource(R.string.mFlops)
        "$roundedFlops$unit"
    }
    return result
}
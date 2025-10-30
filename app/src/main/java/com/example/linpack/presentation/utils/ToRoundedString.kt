package com.example.linpack.presentation.utils

import com.example.linpack.data.Constants

fun durationToRoundedString(duration: Double): String {
    val decimalPlaces = Constants.DURATION_DECIMAL_PLACES
    val result = toRoundedString(
        value = duration,
        decimalPlaces = decimalPlaces,
    )
    return result
}

fun flopsToRoundedString(flops: Double): String {
    val decimalPlaces = Constants.FLOPS_DECIMAL_PLACES
    val result = toRoundedString(
        value = flops,
        decimalPlaces = decimalPlaces,
    )
    return result
}

private fun toRoundedString(value: Double, decimalPlaces: Int): String {
    return "%.${decimalPlaces}f".format(value)
}

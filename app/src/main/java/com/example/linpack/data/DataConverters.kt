package com.example.linpack.data

fun Long.byteToMB(): Double {
    val mb = this / (1024.0 * 1024)
    return mb
}

fun Int.kHzToMHz(): Int {
    val mb = this / 1000
    return mb
}

fun Long.nsToSec(): Double {
    val ms = this / 1_000_000_000.0
    return ms
}

fun Double.flopsToMFlops(): Double {
    val mFlops = this / 1_000_000
    return mFlops
}

fun Int.matrixSizeToEstimatedNumOfOperations(): Double {
    val numOfOperations = (2.0 / 3.0 * this * this * this)
    return numOfOperations
}

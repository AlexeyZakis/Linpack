package com.example.linpack.data

object GaussNative {
    init {
        System.loadLibrary("linpack_native")
    }

    external fun solveGaussian(n: Int, a: FloatArray, b: FloatArray): FloatArray
}

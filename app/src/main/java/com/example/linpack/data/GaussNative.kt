package com.example.linpack.data

object GaussNative {
    init {
        System.loadLibrary("linpack_native")
    }

    // Return required time in ns
    external fun measureGaussian(matrixSize: Int, seed: Int): Long
}

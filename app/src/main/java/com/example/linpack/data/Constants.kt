package com.example.linpack.data

object Constants {
    const val FULLSCREEN: Boolean = false

    const val SEED: Int = 42

    const val NUM_OF_RUNS_DEFAULT: Int = 1
    const val NUM_OF_RUNS_MIN: Int = 1
    const val NUM_OF_RUNS_MAX: Int = 10

    const val MATRIX_SIZE_DEFAULT: Int = 2000
    const val MATRIX_SIZE_MIN: Int = 200
    const val MATRIX_SIZE_MAX: Int = 10000
    const val MATRIX_SIZE_STEP: Int = 200

    const val LINPACK_INFO_MAX_WIDTH: Int = 400

    const val HEURISTICALLY_FLOPS_PER_CYCLE: Int = 8

    const val DURATION_DECIMAL_PLACES: Int = 3
    const val FLOPS_DECIMAL_PLACES: Int = 2

    const val WARMUP_RUNS: Int = 0
}

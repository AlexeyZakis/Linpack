package com.example.linpack.presentation.utils

import com.example.linpack.R
import com.example.linpack.domain.models.GaussImpl

fun GaussImpl.toResId() = when (this) {
    GaussImpl.Cpp -> R.string.cppOpenMP
    GaussImpl.Kotlin -> R.string.kotlinCoroutines
}

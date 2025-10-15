package com.example.linpack.domain.models

enum class GaussImpl {
    Kotlin,
    Cpp,
    ;

    companion object {
        val DEFAULT = Cpp
    }
}
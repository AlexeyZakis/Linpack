package com.example.linpack.domain.usecase.linpackManager

import com.example.linpack.domain.LinpackManager

class GetProgressUseCase(
    private val linpackManager: LinpackManager,
) {
    operator fun invoke() = linpackManager.progress
}

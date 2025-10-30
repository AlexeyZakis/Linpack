package com.example.linpack.domain.usecase.linpackManager

import com.example.linpack.domain.LinpackManager

class GetCurrentRunNumberUseCase(
    private val linpackManager: LinpackManager,
) {
    operator fun invoke() = linpackManager.currentRunNumber
}

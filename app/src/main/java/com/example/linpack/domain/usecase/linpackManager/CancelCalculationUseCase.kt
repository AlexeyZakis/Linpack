package com.example.linpack.domain.usecase.linpackManager

import com.example.linpack.domain.LinpackManager

class CancelCalculationUseCase(
    private val linpackManager: LinpackManager,
) {
    suspend operator fun invoke() = linpackManager.cancelCalculation()
}

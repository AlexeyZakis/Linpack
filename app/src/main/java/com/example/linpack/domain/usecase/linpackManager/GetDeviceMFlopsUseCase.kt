package com.example.linpack.domain.usecase.linpackManager

import com.example.linpack.domain.LinpackManager

class GetDeviceMFlopsUseCase(
    private val linpackManager: LinpackManager,
) {
    suspend operator fun invoke(
        matrixSize: Int,
        cores: Int,
    ) = linpackManager.getDeviceMFlops(
        matrixSize = matrixSize,
        cores = cores,
    )
}

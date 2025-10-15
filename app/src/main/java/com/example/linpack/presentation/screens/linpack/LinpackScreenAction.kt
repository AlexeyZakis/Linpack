package com.example.linpack.presentation.screens.linpack

import com.example.linpack.domain.models.GaussImpl

sealed class LinpackScreenAction {
    data class OnMatrixSizeChanged(val matrixSize: Int) : LinpackScreenAction()
    data class OnCoresChanged(val cores: Int) : LinpackScreenAction()
    data class OnGaussImplChanged(val gaussImpl: GaussImpl) : LinpackScreenAction()
    data object OnRunClick : LinpackScreenAction()
}

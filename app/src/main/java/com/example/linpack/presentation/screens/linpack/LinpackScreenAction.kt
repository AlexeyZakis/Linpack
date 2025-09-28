package com.example.linpack.presentation.screens.linpack

sealed class LinpackScreenAction {
    data class OnMatrixSizeChanged(val matrixSize: Int) : LinpackScreenAction()
    data class OnCoresChanged(val cores: Int) : LinpackScreenAction()
    data object OnRunClick : LinpackScreenAction()
    data object OnCancelClick : LinpackScreenAction()
}

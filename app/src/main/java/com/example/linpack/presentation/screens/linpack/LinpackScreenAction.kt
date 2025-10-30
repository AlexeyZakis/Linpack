package com.example.linpack.presentation.screens.linpack

sealed class LinpackScreenAction {
    data class OnMatrixSizeChanged(val matrixSize: Int) : LinpackScreenAction()
    data class OnNumOfRunsChanged(val numOfRuns: Int) : LinpackScreenAction()
    data class OnCoresChanged(val cores: Int) : LinpackScreenAction()
    data object OnRunClick : LinpackScreenAction()
}

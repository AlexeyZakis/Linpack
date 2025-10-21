package com.example.linpack.presentation.screens.linpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.CountRequiredMemoryMBUseCase
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetAvailableMemoryMBUseCase
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetDeviceResourcesUseCase
import com.example.linpack.domain.usecase.linpackManager.CheckDevicePerformanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LinpackScreenViewModel @Inject constructor(
    private val checkDevicePerformanceUseCase: CheckDevicePerformanceUseCase,
    getDeviceResourcesUseCase: GetDeviceResourcesUseCase,
    countRequiredMemoryMBUseCase: CountRequiredMemoryMBUseCase,
    getAvailableMemoryMBUseCase: GetAvailableMemoryMBUseCase,
) : ViewModel() {
    private val _screenState = MutableStateFlow(LinpackScreenState())
    val screenState = _screenState.asStateFlow()

    val deviceResources = getDeviceResourcesUseCase()

    init {
        _screenState.update { screenState ->
            screenState.copy(
                cores = deviceResources.cores,
                availableMemoryMB = getAvailableMemoryMBUseCase().toInt()
            )
        }
        _screenState.onEach { screenState ->
            val requiredMemoryMB = countRequiredMemoryMBUseCase(
                matrixSize = screenState.matrixSize
            ).toInt()
            val enoughMemory = screenState.availableMemoryMB >= screenState.requiredMemoryMB
            _screenState.update { screenState ->
                screenState.copy(
                    requiredMemoryMB = requiredMemoryMB,
                    enoughMemory = enoughMemory,
                )
            }
        }.launchIn(viewModelScope)
    }

    fun screenAction(action: LinpackScreenAction) {
        when (action) {
            is LinpackScreenAction.OnMatrixSizeChanged -> onMatrixSizeChanged(action.matrixSize)
            is LinpackScreenAction.OnCoresChanged -> onCoresChanged(action.cores)
            is LinpackScreenAction.OnGaussImplChanged -> onGaussImplChanged(action.gaussImpl)
            is LinpackScreenAction.OnRunClick -> onRunClick()
        }
    }

    private fun onMatrixSizeChanged(matrixSize: Int) {
        _screenState.update { screenState ->
            screenState.copy(
                matrixSize = matrixSize,
            )
        }
    }

    private fun onCoresChanged(cores: Int) {
        _screenState.update { screenState ->
            screenState.copy(
                cores = cores,
            )
        }
    }

    private fun onGaussImplChanged(gaussImpl: GaussImpl) {
        _screenState.update { screenState ->
            screenState.copy(
                gaussImpl = gaussImpl,
            )
        }
    }

    private fun onRunClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _screenState.update { screenState ->
                    screenState.copy(
                        inProgress = true,
                        matrixSizeInProgress = screenState.matrixSize,
                        gaussImplInProgress = screenState.gaussImpl,
                    )
                }
                val screenState = screenState.value
                val linpackResult = checkDevicePerformanceUseCase(
                    matrixSize = screenState.matrixSize,
                    cores = screenState.cores,
                    gaussImpl = screenState.gaussImpl,
                )
                _screenState.update { screenState ->
                    screenState.copy(
                        linpackResult = linpackResult,
                        inProgress = false,
                        linpackDone = true,
                    )
                }
            }
        }
    }
}

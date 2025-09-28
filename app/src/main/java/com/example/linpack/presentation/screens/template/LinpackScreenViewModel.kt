package com.example.linpack.presentation.screens.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.CountAvailableMemoryUseCase
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetDeviceResourcesUseCase
import com.example.linpack.domain.usecase.linpackManager.CancelCalculationUseCase
import com.example.linpack.domain.usecase.linpackManager.GetDeviceMFlopsUseCase
import com.example.linpack.domain.usecase.linpackManager.GetProgressUseCase
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
    private val getDeviceMFlopsUseCase: GetDeviceMFlopsUseCase,
    private val cancelCalculationUseCase: CancelCalculationUseCase,
    getDeviceResourcesUseCase: GetDeviceResourcesUseCase,
    countAvailableMemoryUseCase: CountAvailableMemoryUseCase,
    getProgressUseCase: GetProgressUseCase,
) : ViewModel() {
    private val _screenState = MutableStateFlow(LinpackScreenState())
    val screenState = _screenState.asStateFlow()

    val deviceResources = getDeviceResourcesUseCase()
    val linpackProgress = getProgressUseCase()

    init {
        _screenState.update { screenState ->
            screenState.copy(
                cores = deviceResources.cores,
                maxCores = deviceResources.cores,
            )
        }
        linpackProgress.onEach { progress ->
            _screenState.update { screenState ->
                screenState.copy(
                    progress = progress,
                )
            }
        }.launchIn(viewModelScope)

        _screenState.onEach { screenState ->
            val matrixSize = screenState.matrixSize
            val availableMemory = countAvailableMemoryUseCase(matrixSize = matrixSize).toInt()
            _screenState.update { screenState ->
                screenState.copy(
                    availableMemoryMB = availableMemory,
                )
            }
        }.launchIn(viewModelScope)
    }

    fun screenAction(action: LinpackScreenAction) {
        when (action) {
            is LinpackScreenAction.OnMatrixSizeChanged -> onMatrixSizeChanged(action.matrixSize)
            is LinpackScreenAction.OnCoresChanged -> onCoresChanged(action.cores)
            is LinpackScreenAction.OnRunClick -> onRunClick()
            is LinpackScreenAction.OnCancelClick -> onCancelClick()
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

    private fun onRunClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _screenState.update { screenState ->
                    screenState.copy(
                        inProgress = true,
                        coresInProgress = screenState.cores,
                        matrixSizeInProgress = screenState.matrixSize,
                    )
                }
                val screenState = screenState.value
                val matrixSize = screenState.matrixSize
                val cores = screenState.cores
                val linpackResult = getDeviceMFlopsUseCase(
                    matrixSize = matrixSize,
                    cores = cores,
                )
                val durationSec = linpackResult.durationSec
                val mFlops = linpackResult.mFlops
                _screenState.update { screenState ->
                    screenState.copy(
                        durationSec = durationSec,
                        mFlops = mFlops,
                        inProgress = false,
                        isCancelling = false,
                    )
                }
            }
        }
    }

    private fun onCancelClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _screenState.update { screenState ->
                    screenState.copy(
                        isCancelling = true,
                    )
                }
                cancelCalculationUseCase()
            }
        }
    }
}

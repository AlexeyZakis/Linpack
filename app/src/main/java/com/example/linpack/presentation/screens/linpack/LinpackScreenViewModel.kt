package com.example.linpack.presentation.screens.linpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linpack.data.Constants
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetDeviceResourcesUseCase
import com.example.linpack.domain.usecase.deviceResourcesAnalyzer.GetEstimatedCpuMFlopsUseCase
import com.example.linpack.domain.usecase.linpackManager.CheckDevicePerformanceUseCase
import com.example.linpack.domain.usecase.linpackManager.GetCurrentRunNumberUseCase
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
    getEstimatedCpuMFlopsUseCase: GetEstimatedCpuMFlopsUseCase,
    getCurrentRunNumberUseCase: GetCurrentRunNumberUseCase,
) : ViewModel() {
    private val _screenState = MutableStateFlow(LinpackScreenState())
    val screenState = _screenState.asStateFlow()

    val currentRunNumber = getCurrentRunNumberUseCase()

    val deviceResources = getDeviceResourcesUseCase()
    val estimatedCpuMFlops = getEstimatedCpuMFlopsUseCase()

    init {
        currentRunNumber.onEach { currentRunNumber ->
            _screenState.update { screenState ->
                screenState.copy(
                    currentRunNumber = currentRunNumber,
                )
            }
        }.launchIn(viewModelScope)

        _screenState.update { screenState ->
            screenState.copy(
                cores = deviceResources.cores,
                estimatedCpuMFlops = estimatedCpuMFlops,
            )
        }
    }

    fun screenAction(action: LinpackScreenAction) {
        when (action) {
            is LinpackScreenAction.OnMatrixSizeChanged -> onMatrixSizeChanged(action.matrixSize)
            is LinpackScreenAction.OnNumOfRunsChanged -> onNumOfRunsChanged(action.numOfRuns)
            is LinpackScreenAction.OnCoresChanged -> onCoresChanged(action.cores)
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

    private fun onNumOfRunsChanged(numOfRuns: Int) {
        _screenState.update { screenState ->
            screenState.copy(
                numOfRuns = numOfRuns,
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
                        matrixSizeInProgress = screenState.matrixSize,
                        numOfRunsInProgress = screenState.numOfRuns,
                    )
                }
                val screenState = screenState.value
                val linpackResult = checkDevicePerformanceUseCase(
                    matrixSize = screenState.matrixSize,
                    numOfRuns = screenState.numOfRuns,
                    seed = Constants.SEED,
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

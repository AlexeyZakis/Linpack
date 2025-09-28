package com.example.linpack.presentation.screens.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.presentation.screens.template.components.CancelCalculationBtn
import com.example.linpack.presentation.screens.template.components.Cores
import com.example.linpack.presentation.screens.template.components.DeviceResources
import com.example.linpack.presentation.screens.template.components.MatrixSize
import com.example.linpack.presentation.screens.template.components.ProgressState
import com.example.linpack.presentation.screens.template.components.Results
import com.example.linpack.presentation.screens.template.components.RunLinpackBtn
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun LinpackScreen(
    screenState: LinpackScreenState,
    screenAction: (LinpackScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(themeColors.backPrimary)
            .padding(32.dp)
            .padding(top = 100.dp)
    ) {
        DeviceResources(
            cores = screenState.maxCores,
            availableMemoryMB = screenState.availableMemoryMB,
        )
        Cores(
            cores = screenState.cores,
            maxCores = screenState.maxCores,
            onCoresChanged = {
                screenAction(LinpackScreenAction.OnCoresChanged(it.toInt()))
            }
        )
        MatrixSize(
            matrixSize = screenState.matrixSize,
            onMatrixSizeChanged = {
                screenAction(LinpackScreenAction.OnMatrixSizeChanged(it.toInt()))
            }
        )
        if (screenState.inProgress) {
            CancelCalculationBtn(
                enabled = !screenState.isCancelling,
                onClick = { screenAction(LinpackScreenAction.OnCancelClick) },
            )
        } else {
            RunLinpackBtn(
                enabled = !screenState.isCancelling,
                onClick = { screenAction(LinpackScreenAction.OnRunClick) },
            )
        }
        when {
            screenState.inProgress -> {
                ProgressState(
                    cores = screenState.coresInProgress,
                    matrixSize = screenState.matrixSizeInProgress,
                    isCancelling = screenState.isCancelling,
                    progressPercentage = screenState.progress,
                )
            }

            !screenState.inProgress && screenState.mFlops != 0.0 -> {
                Results(
                    mFlops = screenState.mFlops,
                    durationSec = screenState.durationSec,
                    cores = screenState.coresInProgress,
                    matrixSize = screenState.matrixSizeInProgress,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TemplateScreenPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                inProgress = true,
                isCancelling = false,
                cores = 4,
                availableMemoryMB = 300,
                matrixSize = 1000,
                durationSec = 4.235,
                mFlops = 9.46,
                coresInProgress = 4,
                matrixSizeInProgress = 800,
                maxCores = 8,
                progress = 67,
            ),
            screenAction = {},
        )
    }
}

package com.example.linpack.presentation.screens.linpack

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.domain.models.LinpackResult
import com.example.linpack.presentation.screens.linpack.components.CopyResultBtn
import com.example.linpack.presentation.screens.linpack.components.DeviceResources
import com.example.linpack.presentation.screens.linpack.components.InfoDialog
import com.example.linpack.presentation.screens.linpack.components.LinpackInfoBtn
import com.example.linpack.presentation.screens.linpack.components.MatrixSize
import com.example.linpack.presentation.screens.linpack.components.NumOfRuns
import com.example.linpack.presentation.screens.linpack.components.Progress
import com.example.linpack.presentation.screens.linpack.components.Results
import com.example.linpack.presentation.screens.linpack.components.RunLinpackBtn
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun LinpackScreen(
    screenState: LinpackScreenState,
    screenAction: (LinpackScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var showInfoDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = showInfoDialog) {
        showInfoDialog = false
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(themeColors.backPrimary)
            .padding(32.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 32.dp)
        ) {
            DeviceResources(
                cores = screenState.cores,
                estimatedCpuMFlops = screenState.estimatedCpuMFlops,
                isPortrait = isPortrait,
            )
            MatrixSize(
                matrixSize = screenState.matrixSize,
                onMatrixSizeChanged = {
                    screenAction(LinpackScreenAction.OnMatrixSizeChanged(it.toInt()))
                }
            )
            NumOfRuns(
                numOfRuns = screenState.numOfRuns,
                onNumOfRunsChange = {
                    screenAction(LinpackScreenAction.OnNumOfRunsChanged(it.toInt()))
                }
            )
            HorizontalDivider()
            if (screenState.inProgress) {
                Progress(
                    cores = screenState.cores,
                    currentRunNumber = screenState.currentRunNumber,
                    matrixSize = screenState.matrixSizeInProgress,
                    numOfRuns = screenState.numOfRunsInProgress,
                )
            } else {
                RunLinpackBtn(
                    onClick = { screenAction(LinpackScreenAction.OnRunClick) },
                )
            }
            if (!screenState.inProgress && screenState.linpackDone) {
                Results(
                    linpackResult = screenState.linpackResult,
                    estimatedCpuMFlops = screenState.estimatedCpuMFlops,
                    isPortrait = isPortrait,
                    cores = screenState.cores,
                )
                if (screenState.linpackResult is LinpackResult.Success) {
                    CopyResultBtn(
                        cores = screenState.cores,
                        linpackResult = screenState.linpackResult,
                        estimatedCpuMFlops = screenState.estimatedCpuMFlops,
                    )
                }
            }
        }
        LinpackInfoBtn(
            onClick = { showInfoDialog = true },
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
    InfoDialog(
        showDialog = showInfoDialog,
        onDismiss = {
            showInfoDialog = false
        },
    )
}

@Preview
@Composable
private fun LinpackScreenPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = false,
                inProgress = false,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Success(
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                ),
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun LinpackScreenInProgressPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = false,
                inProgress = true,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Success(
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                ),
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun LinpackScreenResultPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = true,
                inProgress = false,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Success(
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                ),
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun LinpackScreenErrorPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = true,
                inProgress = false,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Error,
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun LinpackScreenDarkPreview() {
    AppTheme(
        darkTheme = true,
    ) {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = false,
                inProgress = false,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Success(
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                ),
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun LinpackScreenInProgressDarkPreview() {
    AppTheme(
        darkTheme = true,
    ) {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = false,
                inProgress = true,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Success(
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                ),
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun LinpackScreenResultDarkPreview() {
    AppTheme(
        darkTheme = true,
    ) {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = true,
                inProgress = false,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Success(
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                ),
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun LinpackScreenErrorDarkPreview() {
    AppTheme(
        darkTheme = true,
    ) {
        LinpackScreen(
            screenState = LinpackScreenState(
                linpackDone = true,
                inProgress = false,
                cores = 4,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                numOfRuns = 3,
                numOfRunsInProgress = 4,
                currentRunNumber = 2,
                linpackResult = LinpackResult.Error,
                estimatedCpuMFlops = 124_420,
            ),
            screenAction = {},
        )
    }
}

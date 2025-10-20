package com.example.linpack.presentation.screens.linpack

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.domain.models.LinpackResult
import com.example.linpack.presentation.screens.linpack.components.CopyResultBtn
import com.example.linpack.presentation.screens.linpack.components.DeviceResources
import com.example.linpack.presentation.screens.linpack.components.InfoDialog
import com.example.linpack.presentation.screens.linpack.components.LinpackInfoBtn
import com.example.linpack.presentation.screens.linpack.components.MatrixSize
import com.example.linpack.presentation.screens.linpack.components.Progress
import com.example.linpack.presentation.screens.linpack.components.Results
import com.example.linpack.presentation.screens.linpack.components.RunLinpackBtn
import com.example.linpack.presentation.screens.linpack.components.SelectGauss
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

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(themeColors.backPrimary)
            .padding(32.dp)
            .padding(top = 16.dp)
    ) {
        LinpackInfoBtn(
            onClick = { showInfoDialog = true },
        )
        DeviceResources(
            enoughMemory = screenState.enoughMemory,
            cores = screenState.cores,
            availableMemoryMB = screenState.availableMemoryMB,
            requiredMemoryMB = screenState.requiredMemoryMB,
            isPortrait = isPortrait,
        )
        MatrixSize(
            matrixSize = screenState.matrixSize,
            onMatrixSizeChanged = {
                screenAction(LinpackScreenAction.OnMatrixSizeChanged(it.toInt()))
            }
        )
        SelectGauss(
            selectedGauss = screenState.gaussImpl,
            onSelect = {
                screenAction(LinpackScreenAction.OnGaussImplChanged(it))
            }
        )
        HorizontalDivider()
        if (screenState.inProgress) {
            Progress(
                cores = screenState.cores,
                matrixSize = screenState.matrixSizeInProgress,
                gaussImpl = screenState.gaussImplInProgress,
                isPortrait = isPortrait,
            )
        } else {
            RunLinpackBtn(
                enable = screenState.enoughMemory,
                onClick = { screenAction(LinpackScreenAction.OnRunClick) },
            )
        }
        if (!screenState.inProgress && screenState.linpackDone) {
            Results(
                linpackResult = screenState.linpackResult,
                isPortrait = isPortrait,
            )
            CopyResultBtn(
                linpackResult = screenState.linpackResult,
            )
        }
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
private fun TemplateScreenPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                inProgress = false,
                linpackDone = false,
                cores = 4,
                availableMemoryMB = 300,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                gaussImpl = GaussImpl.DEFAULT,
                gaussImplInProgress = GaussImpl.DEFAULT,
                linpackResult = LinpackResult(
                    cores = 4,
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                    gaussImpl = GaussImpl.DEFAULT,
                )
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun TemplateScreenInProgressPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                inProgress = true,
                linpackDone = false,
                cores = 4,
                availableMemoryMB = 300,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                gaussImpl = GaussImpl.DEFAULT,
                gaussImplInProgress = GaussImpl.DEFAULT,
                linpackResult = LinpackResult(
                    cores = 4,
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                    gaussImpl = GaussImpl.DEFAULT,
                )
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun TemplateScreenResultPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                inProgress = false,
                linpackDone = true,
                cores = 4,
                availableMemoryMB = 300,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                gaussImpl = GaussImpl.DEFAULT,
                gaussImplInProgress = GaussImpl.DEFAULT,
                linpackResult = LinpackResult(
                    cores = 4,
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                    gaussImpl = GaussImpl.DEFAULT,
                )
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun TemplateScreenPortraitPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                inProgress = false,
                linpackDone = false,
                cores = 4,
                availableMemoryMB = 300,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                gaussImpl = GaussImpl.DEFAULT,
                gaussImplInProgress = GaussImpl.DEFAULT,
                linpackResult = LinpackResult(
                    cores = 4,
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                    gaussImpl = GaussImpl.DEFAULT,
                )
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun TemplateScreenInProgressPortraitPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                inProgress = true,
                linpackDone = false,
                cores = 4,
                availableMemoryMB = 300,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                gaussImpl = GaussImpl.DEFAULT,
                gaussImplInProgress = GaussImpl.DEFAULT,
                linpackResult = LinpackResult(
                    cores = 4,
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                    gaussImpl = GaussImpl.DEFAULT,
                )
            ),
            screenAction = {},
        )
    }
}

@Preview
@Composable
private fun TemplateScreenResultPortraitPreview() {
    AppTheme {
        LinpackScreen(
            screenState = LinpackScreenState(
                inProgress = false,
                linpackDone = true,
                cores = 4,
                availableMemoryMB = 300,
                matrixSize = 1000,
                matrixSizeInProgress = 800,
                gaussImpl = GaussImpl.DEFAULT,
                gaussImplInProgress = GaussImpl.DEFAULT,
                linpackResult = LinpackResult(
                    cores = 4,
                    matrixSize = 1000,
                    durationSec = 4.214,
                    mFlops = 6214.631,
                    gaussImpl = GaussImpl.DEFAULT,
                )
            ),
            screenAction = {},
        )
    }
}

package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.presentation.screens.generalComponents.TextWithName
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.utils.mFlopsToGFlops

@Composable
fun DeviceResources(
    cores: Int,
    estimatedCpuMFlops: Int,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
) {
    val estimatedCpuMFlopsFormated = if (estimatedCpuMFlops == 0) {
        stringResource(R.string.estimatedCpuFlopsError)
    } else {
        mFlopsToGFlops(estimatedCpuMFlops.toDouble())
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        TextWithName(
            name = stringResource(R.string.cores),
            text = "$cores",
        )
        TextWithName(
            name = stringResource(R.string.estimatedCpuFlops),
            text = estimatedCpuMFlopsFormated,
            isColumn = isPortrait,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            estimatedCpuMFlops = 124_420,
            isPortrait = false,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesPortraitPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            estimatedCpuMFlops = 124_420,
            isPortrait = true,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesNoMemoryPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            estimatedCpuMFlops = 124_420,
            isPortrait = false,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesPortraitNoMemoryPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            estimatedCpuMFlops = 124_420,
            isPortrait = true,
        )
    }
}

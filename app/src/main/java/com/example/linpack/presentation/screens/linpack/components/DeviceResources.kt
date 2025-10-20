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
import com.example.linpack.presentation.theme.themeColors
import com.example.linpack.presentation.theme.themeTypography

@Composable
fun DeviceResources(
    cores: Int,
    availableMemoryMB: Int,
    requiredMemoryMB: Int,
    enoughMemory: Boolean,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
) {
    val memoryColor = if (enoughMemory) {
        themeTypography.labelPrimary.color
    } else {
        themeColors.red
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
            name = stringResource(R.string.estimatedUsedMemory),
            text = "${requiredMemoryMB}/${availableMemoryMB}${stringResource(R.string.MB)}",
            isColumn = isPortrait,
            color = memoryColor,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            availableMemoryMB = 512,
            requiredMemoryMB = 214,
            isPortrait = false,
            enoughMemory = true,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesPortraitPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            availableMemoryMB = 512,
            requiredMemoryMB = 214,
            isPortrait = true,
            enoughMemory = true,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesNoMemoryPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            availableMemoryMB = 512,
            requiredMemoryMB = 214,
            isPortrait = false,
            enoughMemory = false,
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesPortraitNoMemoryPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            availableMemoryMB = 512,
            requiredMemoryMB = 214,
            isPortrait = true,
            enoughMemory = false,
        )
    }
}

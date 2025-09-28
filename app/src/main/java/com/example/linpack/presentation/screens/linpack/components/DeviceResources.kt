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

@Composable
fun DeviceResources(
    cores: Int,
    availableMemoryMB: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        TextWithName(
            name = stringResource(id = R.string.maxCores),
            text = "$cores"
        )
        TextWithName(
            name = stringResource(id = R.string.estimatedAvailableMemory),
            text = "${availableMemoryMB}MB"
        )
    }
}

@Preview
@Composable
private fun DeviceResourcesPreview() {
    AppTheme {
        DeviceResources(
            cores = 8,
            availableMemoryMB = 245,
        )
    }
}

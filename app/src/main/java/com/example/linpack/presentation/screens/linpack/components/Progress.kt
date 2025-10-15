package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.presentation.screens.generalComponents.TextWithName
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors
import com.example.linpack.presentation.utils.toResId

@Composable
fun Progress(
    cores: Int,
    matrixSize: Int,
    gaussImpl: GaussImpl,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.inProgress),
            color = themeColors.red,
            modifier = modifier,
        )
        TextWithName(
            name = stringResource(R.string.cores),
            text = "$cores"
        )
        TextWithName(
            name = stringResource(R.string.matrixSize),
            text = "$matrixSize"
        )
        TextWithName(
            name = stringResource(R.string.gaussImplementation),
            text = stringResource(gaussImpl.toResId()),
            isColumn = isPortrait,
        )
    }
}

@Preview
@Composable
private fun ProgressPreview() {
    AppTheme {
        Progress(
            cores = 8,
            matrixSize = 240,
            gaussImpl = GaussImpl.DEFAULT,
            isPortrait = false,
        )
    }
}

@Preview
@Composable
private fun ProgressPortraitPreview() {
    AppTheme {
        Progress(
            cores = 8,
            matrixSize = 240,
            gaussImpl = GaussImpl.DEFAULT,
            isPortrait = true,
        )
    }
}

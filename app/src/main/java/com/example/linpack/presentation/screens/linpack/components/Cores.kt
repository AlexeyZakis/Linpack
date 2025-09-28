package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.data.Constants
import com.example.linpack.presentation.screens.generalComponents.ValueSlider
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors


@Composable
fun Cores(
    cores: Int,
    maxCores: Int,
    onCoresChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    ValueSlider(
        valueName = stringResource(id = R.string.cores),
        value = cores.toFloat(),
        minValue = Constants.CORES_MIN.toFloat(),
        maxValue = maxCores.toFloat(),
        sliderColor = themeColors.labelSecondary,
        sliderBackgroundColor = themeColors.labelTertiary,
        valueNameColor = themeColors.labelPrimary,
        onValueChange = onCoresChanged,
        wrapSliderToNewLine = true,
        maxSliderWidth = 400.dp,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun CoresPreview() {
    AppTheme {
        Cores(
            cores = 4,
            maxCores = 8,
            onCoresChanged = {},
        )
    }
}
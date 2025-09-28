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
fun MatrixSize(
    matrixSize: Int,
    onMatrixSizeChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    ValueSlider(
        valueName = stringResource(id = R.string.matrixSize),
        value = matrixSize.toFloat(),
        minValue = Constants.MATRIX_SIZE_MIN.toFloat(),
        maxValue = Constants.MATRIX_SIZE_MAX.toFloat(),
        sliderColor = themeColors.labelSecondary,
        sliderBackgroundColor = themeColors.labelTertiary,
        valueNameColor = themeColors.labelPrimary,
        onValueChange = onMatrixSizeChanged,
        wrapSliderToNewLine = true,
        maxSliderWidth = 400.dp,
        step = Constants.MATRIX_SIZE_STEP.toFloat(),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun MatrixSizePreview() {
    AppTheme {
        MatrixSize(
            matrixSize = 500,
            onMatrixSizeChanged = {},
        )
    }
}

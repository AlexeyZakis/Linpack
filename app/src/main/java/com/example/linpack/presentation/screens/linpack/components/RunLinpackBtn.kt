package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.presentation.screens.generalComponents.ButtonText
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun RunLinpackBtn(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonText(
        text = stringResource(id = R.string.runLinpack),
        modifier = modifier
            .clickable(enabled) { onClick() }
            .background(
                shape = RoundedCornerShape(8.dp),
                color = themeColors.green,
            )
            .padding(8.dp)
    )
}

@Preview
@Composable
private fun RunLinpackBtnPreview() {
    AppTheme {
        RunLinpackBtn(
            enabled = true,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun RunLinpackBtnDisabledPreview() {
    AppTheme {
        RunLinpackBtn(
            enabled = false,
            onClick = {},
        )
    }
}

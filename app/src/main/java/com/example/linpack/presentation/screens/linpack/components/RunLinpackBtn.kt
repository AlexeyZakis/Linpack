package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.presentation.screens.generalComponents.ButtonText
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun RunLinpackBtn(
    enable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = if (enable) {
        themeColors.green
    } else {
        themeColors.red
    }
    ButtonText(
        text = stringResource(R.string.runLinpack),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .clickable(enable) { onClick() }
            .padding(8.dp)
    )
}

@Preview
@Composable
private fun RunLinpackBtnPreview() {
    AppTheme {
        RunLinpackBtn(
            enable = true,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun RunLinpackBtnDisablePreview() {
    AppTheme {
        RunLinpackBtn(
            enable = false,
            onClick = {},
        )
    }
}

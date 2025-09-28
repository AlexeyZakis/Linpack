package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeTypography

@Composable
fun PrimaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = themeTypography.labelPrimary.color,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = themeTypography.labelPrimary,
    )
}

@Preview
@Composable
private fun PrimaryTextPreview() {
    AppTheme {
        PrimaryText(
            text = "Text",
        )
    }
}

package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeTypography

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        textAlign = TextAlign.Center,
        text = text,
        modifier = modifier,
        style = themeTypography.labelPrimary.copy(
            fontSize = themeTypography.labelPrimary.fontSize * 2
        ),
    )
}

@Preview
@Composable
private fun ButtonTextPreview() {
    AppTheme {
        PrimaryText(
            text = "Text",
        )
    }
}

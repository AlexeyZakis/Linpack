package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeTypography

@Composable
fun BoldPrimaryText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = themeTypography.labelPrimary.copy(
            fontWeight = FontWeight.Bold,
        )
    )
}

@Preview
@Composable
private fun BoldPrimaryTextPreview() {
    AppTheme {
        BoldPrimaryText(
            text = "Text",
        )
    }
}

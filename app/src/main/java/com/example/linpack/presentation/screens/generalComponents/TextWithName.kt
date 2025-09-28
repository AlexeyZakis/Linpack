package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.presentation.theme.AppTheme

@Composable
fun TextWithName(
    name: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        BoldPrimaryText(
            text = "$name: ",
        )
        PrimaryText(
            text = text,
        )
    }
}

@Preview
@Composable
private fun TextWithNamePreview() {
    AppTheme {
        TextWithName(
            name = "Name",
            text = "text",
        )
    }
}

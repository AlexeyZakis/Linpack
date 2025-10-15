package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
    isColumn: Boolean = false,
) {
    val arrangement = 2.dp
    val label = "$name: "
    if (isColumn) {
        Column(
            verticalArrangement = Arrangement.spacedBy(arrangement),
            modifier = modifier,
        ) {
            BoldPrimaryText(
                text = label,
            )
            PrimaryText(
                text = text,
            )
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(arrangement),
            modifier = modifier,
        ) {
            BoldPrimaryText(
                text = label,
            )
            PrimaryText(
                text = text,
            )
        }
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

@Preview
@Composable
private fun TextWithNameColumnPreview() {
    AppTheme {
        TextWithName(
            name = "Name",
            text = "text",
            isColumn = true,
        )
    }
}

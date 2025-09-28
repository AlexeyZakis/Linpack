package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AdaptiveContainer(
    modifier: Modifier = Modifier,
    spacedBy: Dp = 0.dp,
    isRow: Boolean = true,
    content: @Composable () -> Unit,
) {
    if (isRow) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacedBy),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            content()
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacedBy),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RowAdaptiveContainerPreview() {
    AdaptiveContainer(
        isRow = true,
    ) {
        Text("Example")
        Text("Example")
    }
}


@Preview(showBackground = true)
@Composable
private fun ColumnAdaptiveContainerPreview() {
    AdaptiveContainer(
        isRow = false,
    ) {
        Text("Example")
        Text("Example")
    }
}

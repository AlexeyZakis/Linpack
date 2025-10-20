package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linpack.presentation.theme.AppTheme

@Composable
fun ContentDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    clickableDialogContent: Boolean = true,
    backgroundColor: Color = Color.Black.copy(alpha = 0.8f),
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = { onDismiss() })
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .addIf(clickableDialogContent) {
                    clickable {}
                }
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun ContentDialogPreview() {
    AppTheme {
        var showDialog by remember { mutableStateOf(false) }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Button(onClick = { showDialog = true }) {
                Text(text = "Show dialog")
            }
            if (showDialog) {
                ContentDialog(
                    onDismiss = { showDialog = false }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(8.dp)
                    ) {
                        Button(onClick = { }) {
                            Text(text = "Click")
                        }
                        Text(
                            text = "Text",
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    }
}

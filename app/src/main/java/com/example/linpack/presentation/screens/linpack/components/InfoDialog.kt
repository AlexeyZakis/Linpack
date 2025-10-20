package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.data.Constants
import com.example.linpack.presentation.screens.generalComponents.ContentDialog
import com.example.linpack.presentation.screens.generalComponents.PrimaryText
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun InfoDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!showDialog) {
        return
    }
    ContentDialog(
        clickableDialogContent = false,
        backgroundColor = themeColors.backPrimary.copy(
            alpha = 0.8f,
        ),
        onDismiss = { onDismiss() },
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .sizeIn(maxWidth = Constants.LINPACK_INFO_MAX_WIDTH.dp)
                .background(
                    color = themeColors.backPrimary,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(16.dp)
        ) {
            PrimaryText(
                text = stringResource(R.string.memoryInfo),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(themeColors.backSecondary)
                    .padding(8.dp)
            )
            PrimaryText(
                text = stringResource(R.string.gaussImplementationsInfo),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(themeColors.backSecondary)
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun InfoDialogPreview() {
    AppTheme {
        InfoDialog(
            onDismiss = {},
            showDialog = true,
        )
    }
}

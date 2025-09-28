package com.example.linpack.presentation.screens.template.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.presentation.screens.generalComponents.TextWithName
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun ProgressState(
    cores: Int,
    matrixSize: Int,
    isCancelling: Boolean,
    progressPercentage: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        val progressText = if (isCancelling) {
            stringResource(R.string.cancelling)
        } else {
            stringResource(R.string.inProgress)
        }
        Text(
            text = progressText,
            color = themeColors.red,
            modifier = modifier,
        )
        TextWithName(
            name = stringResource(id = R.string.progress),
            text = "$progressPercentage%"
        )
        TextWithName(
            name = stringResource(id = R.string.cores),
            text = "$cores"
        )
        TextWithName(
            name = stringResource(id = R.string.matrixSize),
            text = "$matrixSize"
        )
    }
}

@Preview
@Composable
private fun ProgressStatePreview() {
    AppTheme {
        ProgressState(
            cores = 8,
            matrixSize = 240,
            progressPercentage = 67,
            isCancelling = false,
        )
    }
}

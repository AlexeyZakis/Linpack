package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun LinpackInfoBtn(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = { onClick() },
            containerColor = themeColors.backSecondary,
            contentColor = themeColors.labelPrimary,
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            modifier = modifier
                .align(Alignment.TopEnd)
                .offset(x = 24.dp, y = (-16).dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(R.string.infoBtn),
            )
        }
    }
}

@Preview
@Composable
private fun FormulasButtonPreview() {
    AppTheme {
        LinpackInfoBtn(
            onClick = {},
        )
    }
}
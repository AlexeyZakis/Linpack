package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors

@Composable
fun InfoBtn(
    onClick: () -> Unit,
    contentDescriptionResId: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    background: Color = themeColors.backTertiary,
) {
    Icon(
        imageVector = Icons.Default.Info,
        contentDescription = stringResource(contentDescriptionResId),
        tint = themeColors.labelPrimary,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .clickable(enabled) { onClick() }
            .padding(8.dp)
    )
}

@Preview
@Composable
private fun FormulasButtonPreview() {
    AppTheme {
        InfoBtn(
            contentDescriptionResId = R.string.app_name,
            onClick = {},
        )
    }
}
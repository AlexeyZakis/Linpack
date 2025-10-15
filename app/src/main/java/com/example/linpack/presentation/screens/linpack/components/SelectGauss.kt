package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linpack.R
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.presentation.screens.generalComponents.ItemSelector
import com.example.linpack.presentation.screens.generalComponents.PrimaryText
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.utils.toResId

@Composable
fun SelectGauss(
    selectedGauss: GaussImpl,
    onSelect: (GaussImpl) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PrimaryText(
            text = "${stringResource(R.string.gaussImplementation)}:"
        )
        ItemSelector(
            values = GaussImpl.entries,
            valueToStringResId = { it.toResId() },
            onSelect = { onSelect(it) },
            selected = selectedGauss,
            fontSize = 24.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectGaussPreview() {
    AppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            SelectGauss(
                selectedGauss = GaussImpl.DEFAULT,
                onSelect = {},
            )
        }
    }
}

package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.presentation.screens.generalComponents.TextWithName
import com.example.linpack.presentation.theme.AppTheme

@Composable
fun Results(
    durationSec: Double,
    mFlops: Double,
    cores: Int,
    matrixSize: Int,
    modifier: Modifier = Modifier,
) {
    val roundedMFlops = "%.2f".format(mFlops)

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        TextWithName(
            name = stringResource(id = R.string.linpackFlops),
            text = "${roundedMFlops}MFlops"
        )
        TextWithName(
            name = stringResource(id = R.string.linpackDuration),
            text = "${durationSec}s"
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
private fun ResultsPreview() {
    AppTheme {
        Results(
            durationSec = 0.01,
            mFlops = 1.24,
            cores = 8,
            matrixSize = 120,
        )
    }
}

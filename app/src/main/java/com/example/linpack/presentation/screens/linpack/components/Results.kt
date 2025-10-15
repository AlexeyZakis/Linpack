package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.domain.models.LinpackResult
import com.example.linpack.presentation.screens.generalComponents.TextWithName
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.utils.toResId

@Composable
fun Results(
    linpackResult: LinpackResult,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
) {
    val roundedMFlops = "%.2f".format(linpackResult.mFlops)
    val roundedDurationSec = "%.2f".format(linpackResult.durationSec)

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier,
    ) {
        TextWithName(
            name = stringResource(R.string.linpackFlops),
            text = "${roundedMFlops}${stringResource(R.string.mFlops)}"
        )
        TextWithName(
            name = stringResource(R.string.linpackDuration),
            text = "$roundedDurationSec${stringResource(R.string.seconds)}"
        )
        TextWithName(
            name = stringResource(R.string.cores),
            text = "${linpackResult.cores}"
        )
        TextWithName(
            name = stringResource(R.string.matrixSize),
            text = "${linpackResult.matrixSize}"
        )
        TextWithName(
            name = stringResource(R.string.gaussImplementation),
            text = stringResource(linpackResult.gaussImpl.toResId()),
            isColumn = isPortrait,
        )
    }
}

@Preview
@Composable
private fun ResultsPreview() {
    AppTheme {
        Results(
            linpackResult = LinpackResult(
                cores = 4,
                matrixSize = 1000,
                durationSec = 4.214,
                mFlops = 6214.631,
                gaussImpl = GaussImpl.DEFAULT,
            ),
            isPortrait = false,
        )
    }
}

@Preview
@Composable
private fun ResultsPortraitPreview() {
    AppTheme {
        Results(
            linpackResult = LinpackResult(
                cores = 4,
                matrixSize = 1000,
                durationSec = 4.214,
                mFlops = 6214.631,
                gaussImpl = GaussImpl.DEFAULT,
            ),
            isPortrait = true,
        )
    }
}

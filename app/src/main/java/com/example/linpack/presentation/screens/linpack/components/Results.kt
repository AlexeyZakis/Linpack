package com.example.linpack.presentation.screens.linpack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.domain.models.LinpackResult
import com.example.linpack.presentation.screens.generalComponents.PrimaryText
import com.example.linpack.presentation.screens.generalComponents.TextWithName
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors
import com.example.linpack.presentation.utils.durationToRoundedString
import com.example.linpack.presentation.utils.mFlopsToGFlops

@Composable
fun Results(
    linpackResult: LinpackResult,
    estimatedCpuMFlops: Int,
    cores: Int,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
) {
    if (linpackResult is LinpackResult.Error) {
        PrimaryText(
            text = stringResource(R.string.errorMessage),
            color = themeColors.red,
        )
    } else {
        val linpackResult = linpackResult as LinpackResult.Success
        val roundedDurationSec = durationToRoundedString(linpackResult.durationSec)
        val flops = mFlopsToGFlops(linpackResult.mFlops)

        val estimatedCpuMFlopsFormated = mFlopsToGFlops(estimatedCpuMFlops.toDouble())
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = modifier,
        ) {
            TextWithName(
                name = stringResource(R.string.avgFlops),
                text = flops
            )
            TextWithName(
                name = stringResource(R.string.avgDuration),
                text = "$roundedDurationSec${stringResource(R.string.seconds)}"
            )
            TextWithName(
                name = stringResource(R.string.cores),
                text = "$cores"
            )
            TextWithName(
                name = stringResource(R.string.matrixSize),
                text = "${linpackResult.matrixSize}"
            )
            TextWithName(
                name = stringResource(R.string.numOfRuns),
                text = "${linpackResult.numOfRuns}"
            )
            if (estimatedCpuMFlops != 0) {
                TextWithName(
                    name = stringResource(R.string.estimatedCpuFlops),
                    text = estimatedCpuMFlopsFormated,
                    isColumn = isPortrait,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ResultsPreview() {
    AppTheme {
        Results(
            linpackResult = LinpackResult.Success(
                matrixSize = 1000,
                durationSec = 4.214,
                mFlops = 6214.631,
            ),
            estimatedCpuMFlops = 124_420,
            cores = 4,
            isPortrait = false,
        )
    }
}

@Preview
@Composable
private fun ResultsPortraitPreview() {
    AppTheme {
        Results(
            linpackResult = LinpackResult.Success(
                matrixSize = 1000,
                durationSec = 4.214,
                mFlops = 6214.631,
            ),
            estimatedCpuMFlops = 124_420,
            cores = 4,
            isPortrait = true,
        )
    }
}

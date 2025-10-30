package com.example.linpack.presentation.screens.linpack.components

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linpack.R
import com.example.linpack.domain.models.LinpackResult
import com.example.linpack.presentation.screens.generalComponents.ButtonText
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors
import com.example.linpack.presentation.utils.toResultString
import kotlinx.coroutines.launch

@Composable
fun CopyResultBtn(
    linpackResult: LinpackResult.Success,
    estimatedCpuMFlops: Int,
    cores: Int,
    modifier: Modifier = Modifier,
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val data = toResultString(
        result = linpackResult,
        cores = cores,
        estimatedCpuMFlops = estimatedCpuMFlops,
    )
    val linpackResultStr = stringResource(R.string.linpackResult)

    ButtonText(
        text = stringResource(R.string.copyResults),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(themeColors.blue)
            .clickable {
                scope.launch {
                    val clipData = ClipData.newPlainText(linpackResultStr, data)
                    clipboard.setClipEntry(clipData.toClipEntry())
                }
            }
            .padding(8.dp)
    )
}

@Preview
@Composable
private fun CopyResultBtnPreview() {
    AppTheme {
        CopyResultBtn(
            linpackResult = LinpackResult.Success(
                matrixSize = 1000,
                durationSec = 4.214,
                mFlops = 6214.631,
            ),
            cores = 8,
            estimatedCpuMFlops = 124_420,
        )
    }
}

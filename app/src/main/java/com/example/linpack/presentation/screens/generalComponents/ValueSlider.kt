package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.linpack.presentation.theme.AppTheme
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValueSlider(
    valueName: String,
    value: Float,
    minValue: Float,
    maxValue: Float,
    sliderColor: Color,
    modifier: Modifier = Modifier,
    maxSliderWidth: Dp = Dp.Unspecified,
    valueNameColor: Color = sliderColor,
    sliderBackgroundColor: Color,
    wrapSliderToNewLine: Boolean = false,
    step: Float = 1f,
    onValueChange: (Float) -> Unit,
) {
    val newValueMin = minValue / step
    val newValueMax = maxValue / step
    val normalizedValue = normalizeValue(
        value = value / step,
        minValue = newValueMin,
        maxValue = newValueMax,
    )
    AdaptiveContainer(
        isRow = !wrapSliderToNewLine,
        spacedBy = 8.dp,
        modifier = modifier,
    ) {
        ColorValueText(
            colorName = valueName,
            maxValue = maxValue.toInt() * step,
            color = valueNameColor,
            value = (normalizedValue.toInt() * step).coerceIn(
                minimumValue = minValue,
                maximumValue = maxValue,
            ),
        )
        Slider(
            value = normalizedValue,
            onValueChange = {
                onValueChange(
                    (it.toInt().toFloat() * step).coerceIn(
                        minimumValue = minValue,
                        maximumValue = maxValue,
                    )
                )
            },
            valueRange = newValueMin..newValueMax,
            modifier = Modifier.sizeIn(maxWidth = maxSliderWidth),
            colors = SliderDefaults.colors(
                thumbColor = sliderColor,
                activeTrackColor = sliderColor,
                inactiveTrackColor = sliderBackgroundColor,
            ),
            thumb = {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(16.dp)
                        .background(sliderColor.copy(alpha = 1f))
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    thumbTrackGapSize = 0.dp,
                    colors = SliderDefaults.colors(
                        thumbColor = sliderColor,
                        activeTrackColor = sliderColor,
                        inactiveTrackColor = sliderBackgroundColor,
                    )
                )
            },
        )
    }
}

@Composable
private fun ColorValueText(
    colorName: String,
    maxValue: Float,
    value: Float,
    color: Color
) {
    Box {
        Text(
            text = "$colorName: ${value.toInt()}",
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            color = color,
        )
        Text(
            text = "$colorName: ${maxValue.toInt()}",
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            color = color,
            modifier = Modifier.alpha(0f)
        )
    }
}

private fun normalizeValue(value: Float, minValue: Float, maxValue: Float) =
    min(max(value, minValue), maxValue)

@Preview
@Composable
private fun NoMaxTextSampleValueSliderPreview() {
    var value = remember { 120f }
    AppTheme {
        ValueSlider(
            valueName = "R",
            value = value,
            minValue = 0f,
            maxValue = 255f,
            sliderColor = Color.Red,
            sliderBackgroundColor = Color.Gray,
            valueNameColor = Color.Yellow,
            onValueChange = { value = it },
        )
    }
}

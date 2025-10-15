package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linpack.domain.models.GaussImpl
import com.example.linpack.presentation.theme.AppTheme
import com.example.linpack.presentation.theme.themeColors
import com.example.linpack.presentation.theme.themeTypography
import com.example.linpack.presentation.utils.toResId

@Composable
fun <T> ItemSelector(
    values: List<T>,
    valueToStringResId: (T) -> Int,
    onSelect: (T) -> Unit,
    selected: T,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = themeTypography.labelPrimary.fontSize,
    selectedPrefix: String = "",
    valueColors: Map<T, Color> = mapOf(),
    valuePrefix: Map<T, String> = mapOf(),
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = "$selectedPrefix${stringResource(valueToStringResId(selected))}",
            color = themeColors.labelPrimary,
            fontSize = fontSize,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(themeColors.backSecondary)
                .clickable { expanded = true }
                .padding(8.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = themeColors.backSecondary
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(themeColors.backSecondary)
                    .padding(8.dp)
            ) {
                values.forEach { value ->
                    val backgroundColor = if (value == selected) {
                        themeColors.backTertiary
                    } else {
                        themeColors.backSecondary
                    }
                    val text = (valuePrefix[value] ?: "") +
                            stringResource(valueToStringResId(value))
                    val color = valueColors[value] ?: themeColors.labelPrimary
                    Text(
                        text = text,
                        color = color,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(backgroundColor)
                            .clickable {
                                expanded = false
                                onSelect(value)
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemSelectorPreview() {
    AppTheme {
        var selected by remember { mutableStateOf(GaussImpl.DEFAULT) }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ItemSelector(
                values = GaussImpl.entries,
                valueToStringResId = { it.toResId() },
                onSelect = { selected = it },
                selected = selected,
                fontSize = 24.sp,
                selectedPrefix = "Language: ",
                valueColors = mapOf(
                    GaussImpl.Cpp to Color.Red,
                    GaussImpl.Kotlin to Color.Blue,
                ),
                valuePrefix = mapOf(
                    GaussImpl.Cpp to "The ",
                    GaussImpl.Kotlin to "Great ",
                ),
            )
        }
    }
}

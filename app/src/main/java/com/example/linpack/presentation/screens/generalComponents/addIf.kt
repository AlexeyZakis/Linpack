package com.example.linpack.presentation.screens.generalComponents

import androidx.compose.ui.Modifier

inline fun Modifier.addIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier = if (condition) {
    then(modifier(Modifier))
} else {
    this
}

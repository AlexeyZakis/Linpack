package com.example.linpack.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.linpack.presentation.theme.themes.MainTheme


abstract class Theme {
    /// Colors
    // Light
    abstract val lightLabelPrimary: Color
    abstract val lightLabelSecondary: Color
    abstract val lightLabelTertiary: Color
    abstract val lightLabelDisable: Color

    abstract val lightBackPrimary: Color
    abstract val lightBackSecondary: Color
    abstract val lightBackTertiary: Color
    abstract val lightBackDisable: Color

    abstract val lightRed: Color
    abstract val lightGreen: Color
    abstract val lightBlue: Color
    abstract val lightGray: Color
    abstract val lightGrayLight: Color
    abstract val lightWhite: Color
    abstract val lightYellow: Color
    abstract val lightViolet: Color

    // Dark
    abstract val darkLabelPrimary: Color
    abstract val darkLabelSecondary: Color
    abstract val darkLabelTertiary: Color
    abstract val darkLabelDisable: Color

    abstract val darkBackPrimary: Color
    abstract val darkBackSecondary: Color
    abstract val darkBackTertiary: Color
    abstract val darkBackDisable: Color

    abstract val darkRed: Color
    abstract val darkGreen: Color
    abstract val darkBlue: Color
    abstract val darkGray: Color
    abstract val darkGrayLight: Color
    abstract val darkWhite: Color
    abstract val darkYellow: Color
    abstract val darkViolet: Color

    /// Typography
    abstract val labelPrimary: TextStyle
    abstract val labelSecondary: TextStyle
    abstract val labelTertiary: TextStyle
    abstract val labelDebug: TextStyle
}

data class ColorNames(
    val labelPrimary: Color,
    val labelSecondary: Color,
    val labelTertiary: Color,
    val labelDisable: Color,

    val backPrimary: Color,
    val backSecondary: Color,
    val backTertiary: Color,
    val backDisable: Color,

    val red: Color,
    val green: Color,
    val blue: Color,
    val gray: Color,
    val greyLight: Color,
    val white: Color,
    val yellow: Color,
    val violet: Color
)

val LocalColorNames = staticCompositionLocalOf {
    ColorNames(
        labelPrimary = Color.Unspecified,
        labelSecondary = Color.Unspecified,
        labelTertiary = Color.Unspecified,
        labelDisable = Color.Unspecified,

        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backTertiary = Color.Unspecified,
        backDisable = Color.Unspecified,

        red = Color.Unspecified,
        green = Color.Unspecified,
        blue = Color.Unspecified,
        gray = Color.Unspecified,
        greyLight = Color.Unspecified,
        white = Color.Unspecified,
        yellow = Color.Unspecified,
        violet = Color.Unspecified,
    )
}

data class TypographyNames(
    val labelPrimary: TextStyle,
    val labelSecondary: TextStyle,
    val labelTertiary: TextStyle,
    val labelDebug: TextStyle,
)

val LocalTypographyNames = staticCompositionLocalOf {
    TypographyNames(
        labelPrimary = TextStyle.Default,
        labelSecondary = TextStyle.Default,
        labelTertiary = TextStyle.Default,
        labelDebug = TextStyle.Default,
    )
}

@Composable
fun AppTheme(
    theme: Theme = MainTheme,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val themeColors = if (darkTheme) {
        ColorNames(
            labelPrimary = theme.darkLabelPrimary,
            labelSecondary = theme.darkLabelSecondary,
            labelTertiary = theme.darkLabelTertiary,
            labelDisable = theme.darkLabelDisable,

            backPrimary = theme.darkBackPrimary,
            backSecondary = theme.darkBackSecondary,
            backTertiary = theme.darkBackTertiary,
            backDisable = theme.darkBackDisable,

            red = theme.darkRed,
            green = theme.darkGreen,
            blue = theme.darkBlue,
            gray = theme.darkGray,
            greyLight = theme.darkGrayLight,
            white = theme.darkWhite,
            yellow = theme.darkYellow,
            violet = theme.darkViolet,
        )
    } else {
        ColorNames(
            labelPrimary = theme.lightLabelPrimary,
            labelSecondary = theme.lightLabelSecondary,
            labelTertiary = theme.lightLabelTertiary,
            labelDisable = theme.lightLabelDisable,

            backPrimary = theme.lightBackPrimary,
            backSecondary = theme.lightBackSecondary,
            backTertiary = theme.lightBackTertiary,
            backDisable = theme.lightBackDisable,

            red = theme.lightRed,
            green = theme.lightGreen,
            blue = theme.lightBlue,
            gray = theme.lightGray,
            greyLight = theme.lightGrayLight,
            white = theme.lightWhite,
            yellow = theme.lightYellow,
            violet = theme.lightViolet,
        )
    }
    val typography = TypographyNames(
        labelPrimary = theme.labelPrimary.copy(
            color = themeColors.labelPrimary,
        ),
        labelSecondary = theme.labelSecondary.copy(
            color = themeColors.labelSecondary,
        ),
        labelTertiary = theme.labelTertiary.copy(
            color = themeColors.labelTertiary,
        ),
        labelDebug = theme.labelDebug.copy(
            color = themeColors.labelPrimary,
        ),
    )
    CompositionLocalProvider(
        LocalTypographyNames provides typography,
        LocalColorNames provides themeColors,
    ) {
        MaterialTheme(
            content = content
        )
    }
}

val themeTypography: TypographyNames
    @Composable
    get() = LocalTypographyNames.current

val themeColors: ColorNames
    @Composable
    get() = LocalColorNames.current

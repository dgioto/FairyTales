package com.dgioto.fairytalesinukrainian.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    /*
    primary = Color.Green,
    onPrimary = Color.Green,
    secondary = Color.Green,
    onSecondary = Color.Green,
    background = Color.Blue,
    surface = Color.Yellow,
    */
    )

private val LightColorPalette = lightColorScheme(
    /*
    primary = Color.Green,
    onPrimary = Color.Green,
    secondary = Color.Green,
    onSecondary = Color.Green,
    background = Color.Blue,
    surface = Color.Yellow,
    */
)

@Composable
fun FairyTalesInUkrainianTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}
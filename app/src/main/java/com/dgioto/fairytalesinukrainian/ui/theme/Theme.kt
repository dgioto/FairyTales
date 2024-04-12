package com.dgioto.fairytalesinukrainian.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color.DarkGray,
    onPrimary = Color.LightGray,
    secondary = Color.LightGray,
    onSecondary = Color.DarkGray,
    background = Color.Black,
    surface = Color.Gray
)

private val LightColorPalette = lightColorScheme(
    primary = Yellow500,
    onPrimary = Color.White,
    secondary = Blue500,
    onSecondary = Blue500,
    background = Color.White,
    surface = Blue900
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
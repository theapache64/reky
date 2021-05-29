package com.theapache64.reky.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = BigStone,
    onPrimary = Color.White,
    secondary = BigStone,
    onSecondary = Color.White,
    surface = BigStone,
    onSurface = Color.White
)


@Composable
fun RekyTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
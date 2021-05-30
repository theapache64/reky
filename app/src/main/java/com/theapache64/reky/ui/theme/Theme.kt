package com.theapache64.reky.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


private val DarkColorPalette = darkColors(
    primary = BigStone,
    onPrimary = Color.White,
    secondary = BigStone,
    onSecondary = Color.White,
    surface = BigStone,
    onSurface = Color.White,
    background = BigStone,
    onBackground = Color.White
)


@Composable
fun RekyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(all = 10.dp)
            ) {
                content()
            }
        }
    )
}
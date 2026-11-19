package com.example.movi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object MoviColors {

    // Colores principales
    val Red = Color(0xFFC8213A)
    val RedDark = Color(0xFFC8203A)
    val RedLight = Color(0xFFF9786E)

    val Yellow = Color(0xFFFFC40C)
    val Orange = Color(0xFFF0310A)

    // Textos
    val Navy = Color(0xFF2B2D5B)
    val Ink = Navy
    val GrayText = Color(0xFF6B6874)
    val GrayLight = Color(0xFF8F8C96)
    val Gray = GrayText
    val Muted = GrayLight

    // Fondos
    val Bg = Color(0xFFEFEFF6)
    val Background = Bg
    val White = Color.White

    // Bordes y separadores
    val Border = Color(0xFFD9D9E3)
    val Divider = Color(0xFFEFEFF6)

    // Estados positivos
    val Green = Color(0xFF1E9E5A)
    val GreenBg = Color(0xFFDDF5E7)

    // Acentos
    val PinkBg = Color(0xFFFDE3E6)
    val YellowBg = Color(0xFFFFF3CC)
    val YellowText = Color(0xFFE0A800)

    val Blue = Color(0xFF3B5BDB)
    val BlueBg = Color(0xFFE6EEFF)

    // Alertas
    val Danger = Color(0xFFD92D20)
    val DangerBg = Color(0xFFFDECEE)
}

@Composable
fun MoviTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = MoviColors.Red,
            onPrimary = Color.White,
            secondary = MoviColors.Yellow,
            background = MoviColors.Bg,
            onBackground = MoviColors.Navy,
            surface = Color.White,
            onSurface = MoviColors.Navy,
            error = MoviColors.Danger
        ),
        content = content
    )
}
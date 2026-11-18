package com.example.movi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object MoviColors {
    // Colores corporativos principales
    val Red = Color(0xFFE8161B)
    val RedDark = Color(0xFFC8203A)
    val RedLight = Color(0xFFF5736B)
    val Yellow = Color(0xFFFFC400)
    val Orange = Color(0xFFFF8A2B)
    val GrayLight = Color(0xFFAAAAAA)

    // Fondos y neutros
    val Bg = Color(0xFFECEEF8)
    val Background = Color(0xFFFFEB3B) // Alias para compatibilidad con PerfilScreen
    val Ink = Color(0xFF1B1B3A)
    val Navy = Color(0xFF1B1B3A)       // Alias para textos principales
    val Muted = Color(0xFF8A8AA0)
    val Gray = Color(0xFF8A8AA0)       // Alias para textos secundarios
    val Border = Color(0xFFD9D9E3)
    val Divider = Color(0xFFE2E2EC)    // Tono de líneas divisorias

    // Acentos y fondos de tarjetas / chips
    val Green = Color(0xFF2E9E5B)
    val GreenBg = Color(0xFFE6F5EC)
    val PinkBg = Color(0xFFFDE3E6)
    val YellowBg = Color(0xFFFFF7DB)
    val YellowText = Color(0xFF8F6B00)
    val Blue = Color(0xFF1A73E8)
    val BlueBg = Color(0xFFE8F0FE)

    // Alertas y errores
    val Danger = Color(0xFFD93838)
    val DangerBg = Color(0xFFFDE8E8)
}

@Composable
fun MoviTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = MoviColors.Red,
            background = MoviColors.Background,
            surface = Color.White
        ),
        content = content
    )
}
package com.example.movi.Data.Model


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

enum class CategoriaBeneficio(val etiqueta: String) {
    ROPA("Ropa"),
    COMIDA("Comida"),
    ENTRETENIMIENTO("Entretenimiento"),
}

data class AliadoUi(
    val nombre: String,
    val categoria: CategoriaBeneficio,
    val oferta: String,
    val puntos: Int,
    val colorMarca: Color,
    val logo: Painter? = null,
)

data class BeneficiosUi(
    val puntosDisponibles: Int,
    val esEstudiante: Boolean,
    val aliados: List<AliadoUi>,
)
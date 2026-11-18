package com.example.movi.Data.Model

data class RecompensaUi(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val costoPuntos: Int,
    val categoria: String,
    val imagenRes: Int? = null // O URL si usas Coil/Glide
)

data class PasajeroPuntosUi(
    val nombre: String,
    val puntosDisponibles: Int,
    val recompensasDisponibles: List<RecompensaUi>
)
package com.example.movi.Data.Model


data class RecompensaResponse(
    val idRecompensa: Long,
    val titulo: String,
    val descripcionRecompensa: String?,
    val stockDisponible: Int,
    val costoPuntos: Int,
    val imagenUrl: String?,
    val activa: Boolean?
)

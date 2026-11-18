package com.example.movi.Data.Model

data class PerfilResponse(
    val nombre: String,
    val tipoCuenta: String,
    val correo: String,
    val identificacion: String,
    val fotoUrl: String?,
    val tarjeta: TarjetaDto?
)

data class TarjetaDto(
    val ultimos4: String
)

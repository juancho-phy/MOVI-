package com.example.movi.Data.Model

data class PerfilResponse(
    val nombre: String? = null,
    val tipoCuenta: String? = null,
    val correo: String? = null,
    val identificacion: String? = null,
    val fotoUrl: String? = null,
    val tarjeta: TarjetaDto? = null
)

data class TarjetaDto(
    val ultimos4: String? = null
)

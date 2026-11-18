package com.example.movi.Data.Model

import androidx.compose.ui.graphics.painter.Painter

data class TarjetaUi(
    val ultimos4: String
)

data class PerfilUi(
    val nombre: String = "",
    val tipoCuenta: String = "",
    val correo: String = "",
    val identificacion: String = "",
    val foto: Painter? = null,
    val tarjeta: TarjetaUi? = null
)

enum class ServicioAtencion {
    LINEA,
    CHAT,
    PQRS
}

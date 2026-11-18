package com.example.movi.Data.Model

enum class TipoCuenta {
    INSTITUCIONAL,
    PERSONAL
}

data class DashboardUsuarioUi(
    val nombre: String,
    val tipoCuenta: TipoCuenta
)

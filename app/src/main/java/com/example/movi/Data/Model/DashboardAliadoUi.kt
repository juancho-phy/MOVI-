package com.example.movi.Data.Model

data class DashboardAliadoUi(
    val negocio: String,
    val categoria: String,
    val productos: Int,
    val pendientes: Int,
    val canjesHoy: Int
)

sealed interface DashboardAliadoState {

    data object Loading : DashboardAliadoState

    data class Success(
        val dashboard: DashboardAliadoUi
    ) : DashboardAliadoState

    data class Error(
        val mensaje: String
    ) : DashboardAliadoState
}
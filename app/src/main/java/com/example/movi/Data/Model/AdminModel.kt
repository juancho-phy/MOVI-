package com.example.movi.Data.Model

data class AdminEstadisticasUi(
    val viajesSimulados: String = "12.480",
    val puntosEmitidos: String = "124.800",
    val canjesRealizados: String = "1.026",
    val solicitudesPendientes: Int = 3
)

data class SolicitudAliadoUi(
    val id: String,
    val negocio: String,
    val correo: String,
    val categoria: String,
    val direccion: String
)

data class UsuarioAdminUi(
    val id: String,
    val nombre: String,
    val estado: String, // "Activo" o "Bloqueado: actividad sospechosa"
    val activo: Boolean
)

enum class AdminTab {
    RESUMEN, SOLICITUDES, USUARIOS, CATALOGO, CONFIGURACION
}

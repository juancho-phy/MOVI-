package com.example.movi.Data.Model.Repository

import com.example.movi.Data.Model.DashboardAliadoUi
import com.example.movi.Data.Model.Remote.AliadoApiService

class DashboardAliadoRepositoryImpl(
    private val apiService: AliadoApiService
) : DashboardAliadoRepository {

    override suspend fun obtenerDashboard(): DashboardAliadoUi {

        val response = apiService.obtenerMisPublicaciones()

        if (!response.isSuccessful) {
            throw Exception("Error al obtener las publicaciones: ${response.code()}")
        }

        val publicaciones = response.body()
            ?: throw Exception("El servidor no devolvió publicaciones")

        return DashboardAliadoUi(
            negocio = "Mi negocio",
            categoria = "Aliado",
            productos = publicaciones.size,
            pendientes = 0,
            canjesHoy = 0
        )
    }
}
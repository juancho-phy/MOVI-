package com.example.movi.Data.Model.Repository

import com.example.movi.Data.Model.PerfilResponse
import com.example.movi.Data.Model.Remote.PerfilApiService
import retrofit2.Response

class PerfilRepository(
    private val apiService: PerfilApiService
) {

    suspend fun getPerfil(): Response<PerfilResponse> {
        return apiService.obtenerPerfil()
    }

    suspend fun deleteTarjeta(): Response<Unit> {
        return apiService.eliminarTarjeta()
    }

    suspend fun logout(): Response<Unit> {
        return apiService.cerrarSesion()
    }
}
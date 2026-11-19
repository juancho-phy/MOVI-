package com.example.movi.Data.Model.Remote

import com.example.movi.Data.Model.AdminEstadisticasUi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Header

interface AdminApiService {

    @GET("api/admin/estadisticas")
    suspend fun obtenerEstadisticasAdmin(
        @Header("Authorization") token: String
    ): Response<AdminEstadisticasUi>

    @PUT("api/admin/aliados/{id}/aprobar")
    suspend fun aprobarAliado(
        @Header("Authorization") token: String,
        @Path("id") aliadoId: String
    ): Response<Unit>

    @PUT("api/admin/aliados/{id}/rechazar")
    suspend fun rechazarAliado(
        @Header("Authorization") token: String,
        @Path("id") aliadoId: String
    ): Response<Unit>

    @PUT("api/admin/usuarios/{id}/toggle-bloqueo")
    suspend fun toggleBloqueoUsuario(
        @Header("Authorization") token: String,
        @Path("id") usuarioId: String
    ): Response<Unit>
}
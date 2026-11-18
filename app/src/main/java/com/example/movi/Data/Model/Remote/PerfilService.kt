package com.example.movi.Data.Model.Remote

import com.example.movi.Data.Model.PerfilResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface PerfilApiService {

    @GET("api/perfil")
    suspend fun obtenerPerfil(): Response<PerfilResponse>

    @DELETE("api/tarjeta")
    suspend fun eliminarTarjeta(): Response<Unit>

    @POST("api/auth/logout")
    suspend fun cerrarSesion(): Response<Unit>
}
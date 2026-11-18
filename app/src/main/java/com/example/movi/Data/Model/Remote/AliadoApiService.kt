package com.example.movi.Data.Model.Remote


import com.example.movi.Data.Model.RecompensaResponse
import retrofit2.Response
import retrofit2.http.GET

interface AliadoApiService {

    @GET("aliados/mis-publicaciones")
    suspend fun obtenerMisPublicaciones():
            Response<List<RecompensaResponse>>
}
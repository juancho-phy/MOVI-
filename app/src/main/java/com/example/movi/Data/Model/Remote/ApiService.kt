package com.example.movi.Data.Model.Remote

import com.example.movi.Data.Model.AuthResponse
import com.example.movi.Data.Model.LoginRequest
import com.example.movi.Data.Model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>
}
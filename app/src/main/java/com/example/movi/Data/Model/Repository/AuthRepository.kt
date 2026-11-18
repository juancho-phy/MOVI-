package com.example.movi.Data.Model.Repository

import com.example.movi.Data.Model.Remote.ApiService
import com.example.movi.Data.Model.Remote.TokenManager
import com.example.movi.Data.Model.AuthResponse
import com.example.movi.Data.Model.LoginRequest
import com.example.movi.Data.Model.RegisterRequest


class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(email.trim(), password))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                tokenManager.saveSession(body.token, body.rol, body.email ?: email)
                Result.success(body)
            } else {
                Result.failure(Exception("Credenciales inválidas (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión con el servidor: ${e.message}"))
        }
    }

    suspend fun register(nombre: String, email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.register(RegisterRequest(nombre.trim(), email.trim(), password))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                tokenManager.saveSession(body.token, body.rol, body.email ?: email)
                Result.success(body)
            } else {
                Result.failure(Exception("No se pudo completar el registro (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
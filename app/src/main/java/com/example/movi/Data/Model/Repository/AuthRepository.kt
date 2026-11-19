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
    suspend fun login(
        email: String,
        password: String
    ): Result<AuthResponse> {

        return try {
            val response = apiService.login(
                LoginRequest(
                    email.trim(),
                    password
                )
            )

            if (response.isSuccessful && response.body() != null) {

                val body = response.body()!!

                tokenManager.saveSession(
                    token = body.token,
                    rol = body.rol ?: "PASAJERO",
                    email = body.email ?: email.trim()
                )

                Result.success(body)

            } else {
                Result.failure(
                    Exception(
                        "Credenciales inválidas (${response.code()}): ${response.errorBody()?.string()}"
                    )
                )
            }

        } catch (e: Exception) {
            Result.failure(
                Exception("Error de conexión con el servidor: ${e.message}")
            )
        }
    }

    suspend fun register(
        nombre: String,
        email: String,
        password: String,
        tipoDocumento: String,
        documentoIdentidad: String
    ): Result<AuthResponse>{

        return try {
            val response = apiService.register(
                RegisterRequest(
                    nombreCompleto = nombre.trim(),
                    correoElectronico = email.trim(),
                    contrasena = password,
                    tipoDocumento = tipoDocumento,
                    documentoIdentidad = documentoIdentidad.trim(),
                    fotoPerfilURL = null
                )
            )

            if (response.isSuccessful && response.body() != null) {

                val body = response.body()!!

                tokenManager.saveSession(
                    token = body.token,
                    rol = body.rol ?: "PASAJERO",
                    email = body.email ?: email.trim()
                )

                Result.success(body)

            } else {
                Result.failure(
                    Exception(
                        "No se pudo completar el registro (${response.code()}): ${
                            response.errorBody()?.string()
                        }"
                    )
                )
            }

        } catch (e: Exception) {
            Result.failure(
                Exception("Error de conexión: ${e.message}")
            )
        }
    }
}


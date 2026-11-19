package com.example.movi.Data.Model.Repository

import com.example.movi.Data.Model.AdminEstadisticasUi
import com.example.movi.Data.Model.Remote.AdminApiService
import com.example.movi.Data.Model.Remote.TokenManager

class AdminRepository(
    private val apiService: AdminApiService,
    private val tokenManager: TokenManager
) {

    private fun obtenerToken(): String {
        val token = tokenManager.getToken()
            ?: throw Exception("No hay una sesión activa")

        return if (token.startsWith("Bearer ")) {
            token
        } else {
            "Bearer $token"
        }
    }

    // Obtener estadísticas del administrador
    suspend fun obtenerEstadisticasAdmin(): Result<AdminEstadisticasUi> {
        return try {
            val response = apiService.obtenerEstadisticasAdmin(obtenerToken())

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(
                    Exception("No se pudieron obtener las estadísticas (${response.code()})")
                )
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Error al consultar estadísticas: ${e.message}")
            )
        }
    }

    // Aprobar aliado
    suspend fun aprobarAliado(aliadoId: String): Result<Unit> {
        return ejecutarAccion {
            apiService.aprobarAliado(obtenerToken(), aliadoId)
        }
    }

    // Rechazar aliado
    suspend fun rechazarAliado(aliadoId: String): Result<Unit> {
        return ejecutarAccion {
            apiService.rechazarAliado(obtenerToken(), aliadoId)
        }
    }

    // Bloquear o desbloquear usuario
    suspend fun toggleBloqueoUsuario(usuarioId: String): Result<Unit> {
        return ejecutarAccion {
            apiService.toggleBloqueoUsuario(obtenerToken(), usuarioId)
        }
    }

    // Manejar respuestas de las operaciones
    private suspend fun ejecutarAccion(
        accion: suspend () -> retrofit2.Response<Unit>
    ): Result<Unit> {
        return try {
            val response = accion()

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    Exception("La operación falló (${response.code()})")
                )
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Error de conexión: ${e.message}")
            )
        }
    }
}
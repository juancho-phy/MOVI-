package com.example.movi.Data.Model

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val nombreCompleto: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val email: String? = null,
    val nombreCompleto: String? = null,
    val rol: String? = null,      // PASAJERO, ALIADO, ADMINISTRADOR
    val puntos: Int? = 0
)
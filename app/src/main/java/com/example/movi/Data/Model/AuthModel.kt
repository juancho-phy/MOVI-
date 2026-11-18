package com.example.movi.Data.Model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("correoElectronico")
    val email: String,

    @SerializedName("contrasena")
    val password: String
)

data class RegisterRequest(
    @SerializedName("nombreCompleto")
    val nombreCompleto: String,

    @SerializedName("correoElectronico")
    val email: String,

    @SerializedName("contrasena")
    val password: String
)

data class AuthResponse(
    @SerializedName("token")
    val token: String,

    @SerializedName(value = "correoElectronico", alternate = ["email"])
    val email: String? = null,

    @SerializedName("nombreCompleto")
    val nombreCompleto: String? = null,

    @SerializedName("rol")
    val rol: String? = null,      // PASAJERO, ALIADO, ADMINISTRADOR

    @SerializedName("puntos")
    val puntos: Int? = 0
)
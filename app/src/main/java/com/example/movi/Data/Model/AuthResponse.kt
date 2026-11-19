package com.example.movi.Data.Model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val token: String,
    val email: String? = null,
    val nombreCompleto: String? = null,
    val rol: String? = null,      // PASAJERO, ALIADO, ADMINISTRADOR
    val puntos: Int? = 0
)

package com.example.movi.Data.Model


import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("correoElectronico")
    val correoElectronico: String,

    @SerializedName("contrasena")
    val contrasena: String
)

data class RegisterRequest(

    @SerializedName("nombreCompleto")
    val nombreCompleto: String,

    @SerializedName("correoElectronico")
    val correoElectronico: String,

    @SerializedName("contrasena")
    val contrasena: String,

    @SerializedName("tipoDocumento")
    val tipoDocumento: String,

    @SerializedName("documentoIdentidad")
    val documentoIdentidad: String,

    @SerializedName("fotoPerfilURL")
    val fotoPerfilURL: String? = null
)


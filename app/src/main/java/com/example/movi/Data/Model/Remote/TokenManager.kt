package com.example.movi.Data.Model.Remote

import android.content.Context

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences("movi_prefs", Context.MODE_PRIVATE)

    fun saveSession(token: String, rol: String?, email: String?) {
        prefs.edit()
            .putString("jwt_token", token)
            .putString("user_rol", rol ?: "PASAJERO")
            .putString("user_email", email)
            .apply()
    }

    fun getToken(): String? = prefs.getString("jwt_token", null)
    fun getRol(): String = prefs.getString("user_rol", "PASAJERO") ?: "PASAJERO"
    fun clearSession() = prefs.edit().clear().apply()
}
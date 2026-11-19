package com.example.movi.Data.Model.Remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenManager.getToken()

        val request = if (!token.isNullOrBlank()) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        Log.d(
            "MOVI_AUTH",
            "Solicitud: ${originalRequest.method} ${originalRequest.url}"
        )
        Log.d(
            "MOVI_AUTH",
            "Token enviado: ${!token.isNullOrBlank()}"
        )

        val response = chain.proceed(request)

        Log.d(
            "MOVI_AUTH",
            "Respuesta HTTP: ${response.code} - ${originalRequest.url}"
        )

        return response
    }
}
package com.example.movi.Data.Model.Remote


import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://tu-backend-movi.onrender.com/api/" // Reemplaza con tu URL real

    private var retrofitInstance: Retrofit? = null

    fun getInstance(context: Context): Retrofit {
        if (retrofitInstance == null) {
            val tokenManager = TokenManager(context.applicationContext)

            // Interceptor para ver los logs de las peticiones en el Logcat (muy útil para depurar)
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Cliente OkHttp configurado con tiempos de espera y los interceptores
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(tokenManager))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            // Instancia de Retrofit con Gson para parsear JSON automáticamente
            retrofitInstance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitInstance!!
    }
}
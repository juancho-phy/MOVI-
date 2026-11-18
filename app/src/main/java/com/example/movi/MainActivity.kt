package com.example.movi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.movi.ui.theme.MoviTheme
import com.example.movi.ui.theme.Navigation.MainNavigation
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.movi.Data.Model.Remote.ApiService
import com.example.movi.Data.Model.Remote.RetrofitClient
import com.example.movi.Data.Model.Remote.PerfilApiService
import com.example.movi.Data.Model.Remote.TokenManager
import com.example.movi.Data.Model.Repository.AuthRepository
import com.example.movi.Data.Model.Repository.PerfilRepository

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleDeepLink(intent)

        setContent {
            MoviTheme {
                val context = LocalContext.current

                val retrofit = remember {
                    RetrofitClient.getInstance(context)
                }

                val tokenManager = remember {
                    TokenManager(context.applicationContext)
                }

                val perfilApiService = remember {
                    retrofit.create(PerfilApiService::class.java)
                }

                val authApiService = remember {
                    retrofit.create(ApiService::class.java)
                }

                val perfilRepository = remember {
                    PerfilRepository(perfilApiService)
                }

                val authRepository = remember {
                    AuthRepository(
                        apiService = authApiService,
                        tokenManager = tokenManager
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(
                        perfilRepository = perfilRepository,
                        authRepository = authRepository
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(currentIntent: Intent?) {
        val data = currentIntent?.data ?: return

        if (data.scheme == "movi" && data.host == "auth-success") {
            val token = data.getQueryParameter("token")

            if (!token.isNullOrBlank()) {
                val tokenManager = TokenManager(applicationContext)

                tokenManager.saveSession(
                    token = token,
                    rol = "PASAJERO",
                    email = null
                )
            }
        }
    }
}
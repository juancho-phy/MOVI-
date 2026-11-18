package com.example.movi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.movi.ui.theme.MoviTheme
import com.example.movi.ui.theme.Navigation.MainNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Capturar el token si la app se abrió mediante el Deep Link
        handleDeepLink(intent)

        setContent {
            MoviTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent)
    }

    // 3. handleDeepLink preparado para recibir Intent? de forma segura
    private fun handleDeepLink(currentIntent: Intent?) {
        val data: Uri? = currentIntent?.data
        if (data != null && data.scheme == "movi" && data.host == "auth-success") {
            val token = data.getQueryParameter("token")

            if (!token.isNullOrEmpty()) {
                // TODO: Guarda el token recibido
            }
        }
    }
}
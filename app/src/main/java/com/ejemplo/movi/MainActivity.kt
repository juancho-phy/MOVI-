package com.ejemplo.movi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ejemplo.movi.ui.navigation.MoviNavHost
import com.ejemplo.movi.ui.theme.MoviTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviTheme {
                MoviNavHost()
            }
        }
    }
}

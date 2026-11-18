package com.ejemplo.movi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ejemplo.movi.ui.screens.login.LoginScreen
import com.ejemplo.movi.ui.screens.register.RegisterScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
}

@Composable
fun MoviNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = modifier,
    ) {
        composable(Routes.LOGIN) {
            // TEMPORAL: navegación de prueba, se reemplaza al implementar la lógica real
            LoginScreen(
                onLoginClick = { navController.navigate(Routes.REGISTER) },
            )
        }
        composable(Routes.REGISTER) {
            // TEMPORAL: navegación de prueba, se reemplaza al implementar la lógica real
            RegisterScreen {
                navController.popBackStack()
            }
        }
    }
}

package com.example.movi.ui.theme.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movi.Data.Model.PerfilUi
import com.example.movi.Data.Model.TarjetaUi
import com.example.movi.ui.theme.Screens.PerfilScreen
import com.example.movi.ui.screens.LoginScreen
import com.example.movi.ui.theme.Screens.RegisterScreen
import com.example.movi.ui.theme.ViewModel.AuthViewModel


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object PassengerHome : Screen("passenger_home")
    object AliadoHome : Screen("aliado_home")
    object AdminHome : Screen("admin_home")
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Pantalla de Login
        composable(route = Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = { role ->
                    when (role.uppercase()) {
                        "PASAJERO" -> {
                            navController.navigate(Screen.PassengerHome.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                        "ALIADO" -> {
                            navController.navigate(Screen.AliadoHome.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                        "ADMINISTRADOR" -> {
                            navController.navigate(Screen.AdminHome.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                        else -> {
                            navController.navigate(Screen.PassengerHome.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        // Pantalla de Registro
        composable(route = Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateBackToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = { role ->
                    navController.navigate(Screen.PassengerHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla Principal del Pasajero (Muestra el Perfil y opciones al iniciar sesión)
        composable(route = Screen.PassengerHome.route) {
            PerfilScreen(
                state = PerfilUi(
                    nombre = "Juan Felipe Quintero",
                    tipoCuenta = "Ingeniería de Sistemas · MOVI+",
                    correo = "juan.quintero@universidad.edu.co",
                    identificacion = "1.000.000.000",
                    tarjeta = TarjetaUi("5678")
                ),
                onTomarFoto = {},
                onElegirGaleria = {},
                onEliminarFoto = {},
                onEliminarTarjeta = {},
                onCambiarTarjeta = {},
                onAgregarTarjeta = {},
                onServicio = { servicio ->
                    // Manejar las opciones de servicio al cliente
                },
                onCerrarSesion = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla Principal - Aliado
        composable(route = Screen.AliadoHome.route) {
            // TODO: Pantalla del Aliado (Escáner QR)
        }

        // Pantalla Principal - Administrador
        composable(route = Screen.AdminHome.route) {
            // TODO: Dashboard del Administrador
        }
    }
}
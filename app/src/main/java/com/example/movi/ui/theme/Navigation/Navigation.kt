package com.example.movi.ui.theme.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movi.Data.Model.MoviTab
import com.example.movi.Data.Model.Repository.AuthRepository
import com.example.movi.Data.Model.Repository.PerfilRepository
import com.example.movi.ui.theme.Components.MoviBottomBar
import com.example.movi.ui.theme.Screens.AgregarTarjetaScreen
import com.example.movi.ui.theme.Screens.BeneficiosRoute
import com.example.movi.ui.theme.Screens.DashboardRoute
import com.example.movi.ui.theme.Screens.LoginRolesRoute
import com.example.movi.ui.screens.LoginScreen// ✅ Import añadido (cámbialo a LoginRoute si así se llama tu composable)
import com.example.movi.ui.theme.Screens.PerfilRoute
import com.example.movi.ui.theme.Screens.RegisterScreen
import com.example.movi.ui.theme.ViewModel.AuthViewModel
import com.example.movi.ui.theme.ViewModel.BeneficiosViewModel
import com.example.movi.ui.theme.ViewModel.DashboardViewModel
import com.example.movi.ui.theme.ViewModel.RolUsuario
import com.example.movi.ui.theme.ViewModel.TarjetaViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object LoginRoles : Screen("login_roles")
    object Dashboard : Screen("dashboard")
    object Tarjeta : Screen("tarjeta")
    object Beneficios : Screen("beneficios")
    object Perfil : Screen("perfil")
}

@Composable
fun MainNavigation(
    perfilRepository: PerfilRepository,
    authRepository: AuthRepository,
    navController: NavHostController = rememberNavController()
) {
    val dashboardViewModel: DashboardViewModel = viewModel()
    val beneficiosViewModel: BeneficiosViewModel = viewModel()
    val tarjetaViewModel: TarjetaViewModel = viewModel()

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    val mostrarBottomBar = currentRoute !in listOf(
        Screen.LoginRoles.route,
        Screen.Login.route,
        Screen.Register.route
    )

    val currentTab = when (currentRoute) {
        Screen.Dashboard.route -> MoviTab.INICIO
        Screen.Tarjeta.route -> MoviTab.TARJETA
        Screen.Beneficios.route -> MoviTab.BENEFICIOS
        Screen.Perfil.route -> MoviTab.PERFIL
        else -> MoviTab.INICIO
    }

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.provideFactory(authRepository)
    )

    var rolSeleccionado by remember {
        mutableStateOf(RolUsuario.PASAJERO)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (mostrarBottomBar) {
                MoviBottomBar(
                    selected = currentTab,
                    onSelect = { tabSeleccionada ->
                        val rutaDestino = when (tabSeleccionada) {
                            MoviTab.INICIO -> Screen.Dashboard.route
                            MoviTab.TARJETA -> Screen.Tarjeta.route
                            MoviTab.BENEFICIOS -> Screen.Beneficios.route
                            MoviTab.PERFIL -> Screen.Perfil.route
                            else -> Screen.Dashboard.route
                        }

                        navController.navigate(rutaDestino) {
                            popUpTo(Screen.Dashboard.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.LoginRoles.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.LoginRoles.route) {
                LoginRolesRoute(
                    onNavegarDashboard = { rol ->
                        rolSeleccionado = rol
                        navController.navigate(Screen.Login.route)
                    }
                )
            }

            // ✅ Destino faltante registrado en el NavHost
            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = authViewModel,
                    onNavigateToRegister = {
                        authViewModel.resetState()
                        navController.navigate(Screen.Register.route)
                    },
                    onLoginSuccess = { _ ->
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.LoginRoles.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Dashboard.route) {
                DashboardRoute(
                    viewModel = dashboardViewModel,
                    onVerBeneficios = {
                        navController.navigate(Screen.Beneficios.route)
                    },
                    onTransporteClick = {
                        navController.navigate(Screen.Tarjeta.route)
                    },
                    onComerciosClick = {
                        navController.navigate(Screen.Beneficios.route)
                    },
                    onAvatarClick = {
                        navController.navigate(Screen.Perfil.route)
                    }
                )
            }

            composable(Screen.Tarjeta.route) {
                AgregarTarjetaScreen(
                    serial = tarjetaViewModel.serial,
                    estado = tarjetaViewModel.estado,
                    onSerialChange = {
                        tarjetaViewModel.actualizarSerial(it)
                    },
                    onValidar = {
                        tarjetaViewModel.validarTarjeta()
                    },
                    onTomarFoto = {},
                    onIrInicio = {
                        navController.navigate(Screen.Dashboard.route)
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Beneficios.route) {
                BeneficiosRoute(
                    viewModel = beneficiosViewModel,
                    onTransporteClick = {
                        navController.navigate(Screen.Tarjeta.route)
                    },
                    onAliadoClick = {}
                )
            }

            composable(Screen.Perfil.route) {
                PerfilRoute(
                    repository = perfilRepository,
                    onCerrarSesion = {
                        navController.navigate(Screen.LoginRoles.route) {
                            popUpTo(Screen.LoginRoles.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    viewModel = authViewModel,
                    onNavigateBackToLogin = {
                        authViewModel.resetState()
                        navController.popBackStack()
                    },
                    onRegisterSuccess = { _ ->
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.LoginRoles.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
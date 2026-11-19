package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.ui.theme.Components.*
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.ViewModel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateBackToLogin: () -> Unit,
    onRegisterSuccess: (String) -> Unit
) {
    var nombreCompleto by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var identificacion by remember { mutableStateOf("") } // TODO: integración identificación
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }
    var aceptaTratamiento by remember { mutableStateOf(false) }

    val state = viewModel.uiState

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onRegisterSuccess(state.userRole)
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviColors.Bg)
    ) {
        DotsGrid(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 10.dp, bottom = 96.dp)
        )

        OrangeRing(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 24.dp, y = 24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AuthHeader(busHeight = 90.dp, bottomPadding = 60.dp)

            Column(
                modifier = Modifier
                    .pullUp(36.dp)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    shadowElevation = 12.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Crear Cuenta de Pasajero",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MoviColors.LinkRed,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        MoviTextField(
                            value = nombreCompleto,
                            onValueChange = { nombreCompleto = it },
                            placeholder = "Nombre completo",
                            leadingIcon = Icons.Outlined.Person
                        )

                        MoviTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = "Correo electrónico",
                            leadingIcon = Icons.Outlined.Email,
                            keyboardType = KeyboardType.Email
                        )

                        MoviTextField(
                            value = identificacion,
                            onValueChange = { identificacion = it },
                            placeholder = "Identificación",
                            leadingIcon = Icons.Outlined.Badge,
                            keyboardType = KeyboardType.Number
                        )

                        MoviTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = "Contraseña",
                            leadingIcon = Icons.Outlined.Lock,
                            isPassword = true,
                            keyboardType = KeyboardType.Password
                        )

                        MoviTextField(
                            value = confirmarPassword,
                            onValueChange = { confirmarPassword = it },
                            placeholder = "Confirmar contraseña",
                            leadingIcon = Icons.Outlined.Lock,
                            isPassword = true,
                            keyboardType = KeyboardType.Password
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MoviCheckRow(
                                checked = aceptaTerminos,
                                onCheckedChange = { aceptaTerminos = it },
                                label = "Acepto los términos y condiciones"
                            )
                            MoviCheckRow(
                                checked = aceptaTratamiento,
                                onCheckedChange = { aceptaTratamiento = it },
                                label = "Acepto el tratamiento de mis datos"
                            )
                        }

                        if (state.errorMessage != null) {
                            Text(
                                text = state.errorMessage,
                                color = MoviColors.Danger,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        MoviPillButton(
                            text = "Registrar",
                            onClick = {
                                viewModel.register(nombreCompleto, email, password, confirmarPassword)
                            },
                            enabled = aceptaTerminos && aceptaTratamiento,
                            loading = state.isLoading,
                            arrow = PillArrow.End
                        )
                    }
                }

                TextButton(onClick = onNavigateBackToLogin) {
                    Text(
                        text = "¿Ya tienes cuenta? Inicia sesión",
                        color = MoviColors.LinkRed,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

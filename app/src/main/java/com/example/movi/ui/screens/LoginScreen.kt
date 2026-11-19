package com.example.movi.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.ui.theme.Components.*
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.ViewModel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    onGoogleLoginClick: () -> Unit = {},
    onMicrosoftLoginClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val state = viewModel.uiState
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess(state.userRole)
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
            AuthHeader()

            Column(
                modifier = Modifier
                    .pullUp(40.dp)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
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
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MoviTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = "Correo electrónico",
                            leadingIcon = Icons.Outlined.Email,
                            keyboardType = KeyboardType.Email
                        )

                        MoviTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = "Contraseña",
                            leadingIcon = Icons.Outlined.Lock,
                            isPassword = true,
                            keyboardType = KeyboardType.Password
                        )

                        if (state.errorMessage != null) {
                            Text(
                                text = state.errorMessage,
                                color = MoviColors.Danger,
                                fontSize = 13.sp
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MoviCheckRow(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                label = "Recordar contraseña"
                            )

                            Text(
                                text = "¿Olvidaste tu contraseña?",
                                color = MoviColors.LinkRed,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable(onClick = onForgotPasswordClick)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        MoviPillButton(
                            text = "entrar a MOVI+",
                            onClick = { viewModel.login(email, password) },
                            loading = state.isLoading
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MoviColors.Border)
                    Text(
                        text = "o continúa con",
                        color = MoviColors.Muted,
                        fontSize = 12.sp
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MoviColors.Border)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val googleUrl = "http://10.0.2.2:8080/oauth2/authorization/google"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl))
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, MoviColors.Border),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Text(
                            text = "Google",
                            color = MoviColors.Ink,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            val microsoftUrl = "http://10.0.2.2:8080/oauth2/authorization/microsoft"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(microsoftUrl))
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, MoviColors.Border),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Text(
                            text = "Microsoft",
                            color = MoviColors.Ink,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }

                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        text = "¿No tienes cuenta? Regístrate aquí",
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

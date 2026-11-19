
package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.R
import com.example.movi.ui.theme.ViewModel.AuthViewModel

private val RegisterRed = Color(0xFFC8213A)
private val RegisterOrange = Color(0xFFF0310A)
private val RegisterYellow = Color(0xFFFFC40C)
private val RegisterNavy = Color(0xFF2B2D5B)
private val RegisterGray = Color(0xFF6B6874)
private val RegisterPlaceholder = Color(0xFF8F8C96)
private val RegisterBorder = Color(0xFFCFCBD6)
private val RegisterBackground = Color(0xFFEFEFF6)
private val RegisterDanger = Color(0xFFD92D20)

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateBackToLogin: () -> Unit,
    onRegisterSuccess: (String) -> Unit
) {
    var nombreCompleto by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var documentoIdentidad by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmarPasswordVisible by remember { mutableStateOf(false) }

    var tipoDocumento by remember {
        mutableStateOf("CEDULA_EXTRANJERIA")
    }

    var expanded by remember { mutableStateOf(false) }

    val tiposDocumento = listOf(
        "CEDULA_CIUDADANIA",
        "CEDULA_EXTRANJERIA",
        "TARJETA_IDENTIDAD",
        "PASAPORTE"
    )

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
            .background(RegisterBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // CABECERA DEL BUS
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 110.dp,
                            bottomEnd = 110.dp
                        )
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.movi_bus_header),
                    contentDescription = "Bus MOVI+",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Image(
                    painter = painterResource(R.drawable.movi_logo),
                    contentDescription = "Logo MOVI+",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp)
                        .width(170.dp)
                        .height(48.dp)
                )
            }

            // TARJETA
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-58).dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Crear cuenta",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RegisterNavy
                    )

                    Text(
                        text = "Únete a MOVI+ y acumula puntos",
                        fontSize = 12.sp,
                        color = RegisterGray,
                        modifier = Modifier.padding(top = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // NOMBRE
                    OutlinedTextField(
                        value = nombreCompleto,
                        onValueChange = { nombreCompleto = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Nombre completo", color = RegisterPlaceholder)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Person, null)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RegisterRed,
                            unfocusedBorderColor = RegisterBorder,
                            focusedTextColor = RegisterNavy,
                            unfocusedTextColor = RegisterNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // CORREO
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Correo electrónico", color = RegisterPlaceholder)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Email, null)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RegisterRed,
                            unfocusedBorderColor = RegisterBorder,
                            focusedTextColor = RegisterNavy,
                            unfocusedTextColor = RegisterNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // TIPO DE DOCUMENTO
                    Box(modifier = Modifier.fillMaxWidth()) {

                        OutlinedTextField(
                            value = tipoDocumento.replace("_", " "),
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text("Tipo de documento")
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Badge, null)
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Seleccionar documento"
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RegisterRed,
                                unfocusedBorderColor = RegisterBorder,
                                focusedTextColor = RegisterNavy,
                                unfocusedTextColor = RegisterNavy
                            )
                        )

                        // Área transparente para abrir el menú
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { expanded = true }
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            tiposDocumento.forEach { tipo ->
                                DropdownMenuItem(
                                    text = {
                                        Text(tipo.replace("_", " "))
                                    },
                                    onClick = {
                                        tipoDocumento = tipo
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // NÚMERO DE DOCUMENTO
                    OutlinedTextField(
                        value = documentoIdentidad,
                        onValueChange = {
                            documentoIdentidad = it.filter { c ->
                                c.isLetterOrDigit()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Número de documento", color = RegisterPlaceholder)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Badge, null)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RegisterRed,
                            unfocusedBorderColor = RegisterBorder,
                            focusedTextColor = RegisterNavy,
                            unfocusedTextColor = RegisterNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // CONTRASEÑA
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Contraseña", color = RegisterPlaceholder)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, null)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = "Mostrar contraseña"
                                )
                            }
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RegisterRed,
                            unfocusedBorderColor = RegisterBorder,
                            focusedTextColor = RegisterNavy,
                            unfocusedTextColor = RegisterNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // CONFIRMAR CONTRASEÑA
                    OutlinedTextField(
                        value = confirmarPassword,
                        onValueChange = { confirmarPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Confirmar contraseña", color = RegisterPlaceholder)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, null)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    confirmarPasswordVisible =
                                        !confirmarPasswordVisible
                                }
                            ) {
                                Icon(
                                    if (confirmarPasswordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = "Mostrar contraseña"
                                )
                            }
                        },
                        singleLine = true,
                        visualTransformation = if (confirmarPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RegisterRed,
                            unfocusedBorderColor = RegisterBorder,
                            focusedTextColor = RegisterNavy,
                            unfocusedTextColor = RegisterNavy
                        )
                    )

                    // ERROR
                    if (state.errorMessage != null) {
                        Text(
                            text = state.errorMessage ?: "",
                            color = RegisterDanger,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // REGISTRAR
                    Button(
                        onClick = {
                            viewModel.register(
                                nombre = nombreCompleto.trim(),
                                email = email.trim(),
                                password = password,
                                confirmarPassword = confirmarPassword,
                                tipoDocumento = tipoDocumento,
                                documentoIdentidad = documentoIdentidad.trim()
                            )
                        },
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RegisterOrange
                        )
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(23.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "CREAR CUENTA",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // VOLVER AL LOGIN
                    TextButton(
                        onClick = onNavigateBackToLogin
                    ) {
                        Text(
                            "¿Ya tienes cuenta? ",
                            color = RegisterGray,
                            fontSize = 13.sp
                        )
                        Text(
                            "Inicia sesión",
                            color = RegisterRed,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 18.dp)
                .size(10.dp)
                .background(RegisterYellow, CircleShape)
        )
    }
}

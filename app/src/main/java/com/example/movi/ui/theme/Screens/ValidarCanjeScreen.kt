package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.Components.IconBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidarCanjeScreen(
    onBack: () -> Unit = {},
    onConfirmarEntrega: (String) -> Unit = {},
    onEscanearOtro: () -> Unit = {}
) {
    var codigo by remember { mutableStateOf("MV-4821") }
    var esValido by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviColors.Bg)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MoviColors.RedDark, MoviColors.Red)
                    ),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Validar canje",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Formulario
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = (-40).dp),
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Código del cliente",
                    color = MoviColors.Navy,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Pídele al cliente el código que aparece en su app, o escanea su QR.",
                    color = MoviColors.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = codigo,
                    onValueChange = {
                        codigo = it
                        esValido = it.isNotBlank()
                    },
                    placeholder = { Text("MV-0000", color = MoviColors.Muted) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.QrCodeScanner,
                            contentDescription = null,
                            tint = MoviColors.Navy
                        )
                    },
                    trailingIcon = {
                        if (esValido) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MoviColors.Green
                            )
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (esValido) MoviColors.Green else MoviColors.Red,
                        unfocusedBorderColor = if (esValido) MoviColors.Green else MoviColors.Border,
                        focusedTextColor = MoviColors.Navy,
                        unfocusedTextColor = MoviColors.Navy
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (esValido) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = MoviColors.GreenBg
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            IconBadge(
                                icon = Icons.Outlined.QrCodeScanner,
                                backgroundColor = MoviColors.GreenBg,
                                tintColor = MoviColors.Green,
                                size = 42.dp
                            )

                            Column {
                                Text(
                                    text = "Canje válido",
                                    color = MoviColors.Green,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Café americano · 80 pts · Camila R.\nEntrega el producto al cliente.",
                                    color = MoviColors.Navy,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { onConfirmarEntrega(codigo) },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Confirmar entrega",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onEscanearOtro,
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.5.dp, MoviColors.Red),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MoviColors.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.QrCodeScanner,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Escanear otro código",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

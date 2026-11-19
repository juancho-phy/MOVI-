package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.Components.IconBadge
import com.example.movi.ui.theme.Components.StatusChip

@Composable
fun PuntosRedimidosScreen(
    productoNombre: String = "Café americano",
    aliadoNombre: String = "Café Aroma",
    puntosCanjeados: Int = 80,
    puntosRestantes: Int = 420,
    codigoCanje: String = "MV-4821",
    fechaCanje: String = "16 nov 2026 · 3:42 p. m.",
    onDescargarClick: () -> Unit = {},
    onVolverInicioClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviColors.Bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header con degradado y check verde
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MoviColors.RedDark,
                                MoviColors.Red,
                                MoviColors.RedLight
                            )
                        ),
                        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    // Círculo blanco exterior con check verde
                    Surface(
                        modifier = Modifier.size(96.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 8.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Surface(
                                modifier = Modifier.size(72.dp),
                                shape = CircleShape,
                                color = MoviColors.GreenBg
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MoviColors.Green,
                                        modifier = Modifier.size(44.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¡Puntos redimidos con éxito!",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Presenta este comprobante al aliado",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                }
            }

            // Tarjeta principal de comprobante
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        IconBadge(
                            icon = Icons.Outlined.ConfirmationNumber,
                            backgroundColor = MoviColors.YellowBg,
                            tintColor = MoviColors.YellowText,
                            size = 48.dp
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Comprobante de Canje",
                                color = MoviColors.Navy,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Detalle de la transacción",
                                color = MoviColors.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = MoviColors.Divider)
                    Spacer(modifier = Modifier.height(16.dp))

                    DetalleFila(etiqueta = "Producto", valor = productoNombre)
                    Spacer(modifier = Modifier.height(10.dp))
                    DetalleFila(etiqueta = "Aliado", valor = aliadoNombre)
                    Spacer(modifier = Modifier.height(10.dp))
                    DetalleFila(
                        etiqueta = "Puntos redimidos",
                        valor = "− $puntosCanjeados pts",
                        valorColor = MoviColors.Red,
                        esBold = true
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    DetalleFila(etiqueta = "Puntos restantes", valor = "$puntosRestantes pts")
                    Spacer(modifier = Modifier.height(10.dp))
                    DetalleFila(etiqueta = "Fecha", valor = fechaCanje)

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = MoviColors.Divider)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Código de Canje",
                        color = MoviColors.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StatusChip(
                        text = codigoCanje,
                        bg = MoviColors.YellowBg,
                        textColor = MoviColors.YellowText
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-20).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onDescargarClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Descargar comprobante",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(onClick = onVolverInicioClick) {
                    Text(
                        text = "Volver al inicio",
                        color = MoviColors.Gray,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun DetalleFila(
    etiqueta: String,
    valor: String,
    valorColor: Color = MoviColors.Navy,
    esBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = etiqueta,
            color = MoviColors.Gray,
            fontSize = 13.sp
        )
        Text(
            text = valor,
            color = valorColor,
            fontSize = 13.sp,
            fontWeight = if (esBold) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}

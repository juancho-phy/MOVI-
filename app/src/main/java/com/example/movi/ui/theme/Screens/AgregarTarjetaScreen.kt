package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.Data.Model.EstadoTarjeta
import com.example.movi.Data.Model.MoviTab
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.Components.MoviBottomBar


// --- Componentes auxiliares requeridos localmente para mantener el diseño intacto ---
@Composable
private fun TuLlaveCard(serialLast: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1B1B3A),
        shadowElevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "tuLlave · $serialLast",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
private fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Red)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
// ---------------------------------------------------------------------------------

@Composable
fun AgregarTarjetaScreen(
    serial: String,                         // solo dígitos, máximo 16
    estado: EstadoTarjeta,
    onSerialChange: (String) -> Unit,
    onValidar: () -> Unit,                  // también se usa para "Intentar de nuevo"
    onTomarFoto: () -> Unit,
    onIrInicio: () -> Unit,
    onBack: () -> Unit,
    onTabSelected: (MoviTab) -> Unit = {},
) {
    val completo = serial.length == 16
    val acento = when (estado) {
        EstadoTarjeta.INGRESANDO -> MoviColors.Red
        EstadoTarjeta.INVALIDA -> MoviColors.Danger
        EstadoTarjeta.GUARDADA -> MoviColors.Green
    }

    Column(Modifier.fillMaxSize().background(MoviColors.Background)) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            // ---- Encabezado con la tarjeta solapada
            Box(Modifier.fillMaxWidth().height(290.dp)) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                        .background(Brush.linearGradient(listOf(MoviColors.Red, MoviColors.RedLight)))
                        .statusBarsPadding()
                ) {
                    Row(Modifier.padding(start = 12.dp, top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier.size(40.dp).background(Color.White.copy(alpha = 0.22f), CircleShape),
                        ) { Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Volver", tint = Color.White) }
                        Text("Mi tarjeta", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp, modifier = Modifier.padding(start = 12.dp))
                    }
                }
                TuLlaveCard(
                    serialLast = if (estado == EstadoTarjeta.GUARDADA) serial.takeLast(4) else "••••",
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(0.77f),
                )
            }

            // ---- Formulario
            Surface(
                modifier = Modifier.padding(horizontal = 24.dp).padding(top = 16.dp, bottom = 24.dp),
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                shadowElevation = 8.dp,
            ) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Vincula tu tarjeta", color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Ingresa los 16 números del serial de tu tarjeta.", color = MoviColors.Gray, fontSize = 12.sp)

                    OutlinedTextField(
                        value = serial,
                        onValueChange = { onSerialChange(it.filter(Char::isDigit).take(16)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = estado != EstadoTarjeta.GUARDADA,
                        shape = RoundedCornerShape(14.dp),
                        placeholder = { Text("0000 0000 0000 0000", color = MoviColors.Muted) },
                        leadingIcon = { Icon(Icons.Outlined.CreditCard, null, tint = Color(0xFF9E9AA6)) },
                        trailingIcon = {
                            when (estado) {
                                EstadoTarjeta.INVALIDA -> Icon(Icons.Outlined.Cancel, null, tint = acento)
                                EstadoTarjeta.GUARDADA -> Icon(Icons.Outlined.CheckCircle, null, tint = acento)
                                else -> Unit
                            }
                        },
                        supportingText = { Text("${serial.length}/16 dígitos", color = MoviColors.Muted, fontSize = 11.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = SerialTransformation,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = acento,
                            unfocusedBorderColor = if (estado == EstadoTarjeta.INGRESANDO) Color(0xFFCFCBD6) else acento,
                            disabledBorderColor = acento,
                            disabledTextColor = MoviColors.Navy,
                            cursorColor = MoviColors.Red,
                        ),
                    )

                    when (estado) {
                        EstadoTarjeta.INGRESANDO -> Mensaje(
                            Icons.Outlined.Info, Color(0xFFFFF6D6), Color(0xFFB07A00),
                            "Tarjeta personalizada requerida",
                            "Debes tener tu tarjeta MOVI+ personalizada. Si no la tienes, no podrás acceder.",
                        )
                        EstadoTarjeta.INVALIDA -> Mensaje(
                            Icons.Outlined.Cancel, MoviColors.DangerBg, MoviColors.Danger,
                            "Tarjeta inválida",
                            "Este serial no corresponde a una tarjeta MOVI+ personalizada. Verifica el número o solicita tu tarjeta.",
                        )
                        EstadoTarjeta.GUARDADA -> Mensaje(
                            Icons.Outlined.CheckCircle, Color(0xFFE6F7EE), MoviColors.Green,
                            "Tarjeta guardada",
                            "Tu tarjeta MOVI+ quedó vinculada a tu cuenta.",
                        )
                    }

                    when (estado) {
                        EstadoTarjeta.INGRESANDO -> GradientButton("Validar tarjeta", onValidar, enabled = completo)
                        EstadoTarjeta.INVALIDA -> GradientButton("Intentar de nuevo", onValidar, enabled = completo)
                        EstadoTarjeta.GUARDADA -> GradientButton("Ir al inicio", onIrInicio)
                    }

                    if (estado != EstadoTarjeta.GUARDADA) {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            HorizontalDivider(Modifier.weight(1f), color = Color(0xFFE4E2EA))
                            Text("o escanéala con la cámara", color = MoviColors.Muted, fontSize = 11.sp)
                            HorizontalDivider(Modifier.weight(1f), color = Color(0xFFE4E2EA))
                        }
                        OutlinedButton(
                            onClick = onTomarFoto,
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.5.dp, MoviColors.Red),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MoviColors.Red),
                        ) {
                            Icon(Icons.Outlined.PhotoCamera, null, Modifier.size(22.dp))
                            Spacer(Modifier.size(10.dp))
                            Text("Tomar foto de la tarjeta", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
        MoviBottomBar(selected = MoviTab.TARJETA, onSelect = onTabSelected)
    }
}

@Composable
private fun Mensaje(icon: ImageVector, bg: Color, fg: Color, titulo: String, detalle: String) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(bg).padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(icon, null, tint = fg, modifier = Modifier.size(22.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(titulo, color = fg, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(detalle, color = MoviColors.Gray, fontSize = 11.sp)
        }
    }
}
private object SerialTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val original = text.text
        val agrupado = original.chunked(4).joinToString(" ")

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                val safeOffset = offset.coerceIn(0, original.length)

                return safeOffset + if (safeOffset == 0) {
                    0
                } else {
                    (safeOffset - 1) / 4
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                val safeOffset = offset.coerceIn(0, agrupado.length)

                return safeOffset - safeOffset / 5
            }
        }

        return TransformedText(
            AnnotatedString(agrupado),
            offsetMapping
        )
    }
}
@Preview(showBackground = true, heightDp = 780, widthDp = 390)
@Composable
private fun TarjetaIngresandoPreview() {
    AgregarTarjetaScreen(
        serial = "45218890",
        estado = EstadoTarjeta.INGRESANDO,
        onSerialChange = {},
        onValidar = {},
        onTomarFoto = {},
        onIrInicio = {},
        onBack = {}
    )
}

@Preview(showBackground = true, heightDp = 780, widthDp = 390)
@Composable
private fun TarjetaInvalidaPreview() {
    AgregarTarjetaScreen(
        serial = "4521889012345678",
        estado = EstadoTarjeta.INVALIDA,
        onSerialChange = {},
        onValidar = {},
        onTomarFoto = {},
        onIrInicio = {},
        onBack = {}
    )
}

@Preview(showBackground = true, heightDp = 780, widthDp = 390)
@Composable
private fun TarjetaGuardadaPreview() {
    AgregarTarjetaScreen(
        serial = "4521889012345678",
        estado = EstadoTarjeta.GUARDADA,
        onSerialChange = {},
        onValidar = {},
        onTomarFoto = {},
        onIrInicio = {},
        onBack = {}
    )
}



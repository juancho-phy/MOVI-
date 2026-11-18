package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movi.Data.Model.PerfilUi
import com.example.movi.Data.Model.Repository.PerfilRepository
import com.example.movi.Data.Model.ServicioAtencion
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.ViewModel.PerfilUiState
import com.example.movi.ui.theme.ViewModel.PerfilViewModel


// ==================== CAPA DE RUTA (ROUTE) ====================
@Composable
fun PerfilRoute(
    repository: PerfilRepository,
    onCerrarSesion: () -> Unit
) {
    val perfilViewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModel.provideFactory(repository)
    )

    when (val state = perfilViewModel.uiState) {
        PerfilUiState.Loading -> {
            Text("Cargando perfil...")
        }

        is PerfilUiState.Error -> {
            Text(state.message)
        }

        is PerfilUiState.Success -> {
            PerfilScreen(
                state = state.perfil,
                onTomarFoto = { perfilViewModel.tomarFoto() },
                onElegirGaleria = { perfilViewModel.elegirGaleria() },
                onEliminarFoto = { perfilViewModel.eliminarFoto() },
                onEliminarTarjeta = { perfilViewModel.eliminarTarjeta() },
                onCambiarTarjeta = { perfilViewModel.cambiarTarjeta() },
                onAgregarTarjeta = { perfilViewModel.agregarTarjeta() },
                onServicio = { servicio ->
                    // Aquí conectaremos LINEA, CHAT y PQRS.
                },
                onCerrarSesion = {
                    perfilViewModel.cerrarSesion(onCerrarSesion)
                }
            )
        }
    }
}

// ==================== CAPA DE VISTA (SCREEN) ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    state: PerfilUi,
    onTomarFoto: () -> Unit,
    onElegirGaleria: () -> Unit,
    onEliminarFoto: () -> Unit,
    onEliminarTarjeta: () -> Unit,
    onCambiarTarjeta: () -> Unit,
    onAgregarTarjeta: () -> Unit,
    onServicio: (ServicioAtencion) -> Unit,
    onCerrarSesion: () -> Unit,
) {
    var mostrarOpcionesFoto by remember { mutableStateOf(false) }
    var mostrarConfirmarEliminar by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MoviColors.Background)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        // ---- Encabezado + avatar
        Box(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(Brush.linearGradient(listOf(MoviColors.Red, MoviColors.RedLight)))
            ) {
                Text(
                    "Mi perfil", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.TopCenter).statusBarsPadding().padding(top = 16.dp),
                )
            }
            Avatar(
                foto = state.foto,
                onCambiar = { mostrarOpcionesFoto = true },
                modifier = Modifier.align(Alignment.BottomCenter).offset(y = 52.dp),
            )
        }
        Spacer(Modifier.height(60.dp))

        Text(
            text = if (state.nombre.isNotBlank()) state.nombre else "Cargando usuario...",
            color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 20.sp,
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(8.dp))

        if (state.tipoCuenta.isNotBlank()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Row(
                    Modifier.clip(RoundedCornerShape(14.dp)).background(MoviColors.PinkBg).padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(Icons.Outlined.School, null, tint = MoviColors.Red, modifier = Modifier.size(14.dp))
                    Text(state.tipoCuenta, color = MoviColors.Red, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }

        Column(
            Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Seccion("Información personal") {
                Tarjeta {
                    InfoRow(Icons.Outlined.Email, MoviColors.PinkBg, MoviColors.Red, "Correo electrónico", if (state.correo.isNotBlank()) state.correo else "---")
                    HorizontalDivider(color = MoviColors.Divider)
                    InfoRow(Icons.Outlined.Badge, MoviColors.YellowBg, MoviColors.YellowText, "Identificación", if (state.identificacion.isNotBlank()) state.identificacion else "---")
                }
            }

            Seccion("Tarjeta asociada") {
                Tarjeta(padding = 20.dp) {
                    val tarjeta = state.tarjeta
                    if (tarjeta != null) {
                        TuLlaveCard(tarjeta.ultimos4, Modifier.fillMaxWidth(0.92f).align(Alignment.CenterHorizontally))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            StatusChip("Vinculada", MoviColors.GreenBg, MoviColors.Green)
                            Text("Serial •••• •••• •••• ${tarjeta.ultimos4}", color = MoviColors.Gray, fontSize = 11.sp)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(
                                onClick = { mostrarConfirmarEliminar = true },
                                modifier = Modifier.weight(1f).height(44.dp),
                                shape = RoundedCornerShape(22.dp),
                                border = BorderStroke(1.5.dp, MoviColors.Danger),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MoviColors.Danger),
                            ) {
                                Icon(Icons.Outlined.DeleteOutline, null, Modifier.size(18.dp))
                                Spacer(Modifier.size(6.dp))
                                Text("Eliminar", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Button(
                                onClick = onCambiarTarjeta,
                                modifier = Modifier.weight(1f).height(44.dp),
                                shape = RoundedCornerShape(22.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Red),
                            ) {
                                Icon(Icons.Outlined.SwapHoriz, null, Modifier.size(18.dp))
                                Spacer(Modifier.size(6.dp))
                                Text("Cambiar tarjeta", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    } else {
                        SinTarjeta(onAgregarTarjeta)
                    }
                }
            }

            Seccion("Servicio al cliente TransMilenio") {
                Tarjeta(padding = 6.dp, spacing = 0.dp) {
                    ServicioRow(Icons.Outlined.Phone, MoviColors.GreenBg, MoviColors.Green, "Línea de atención", "Llama para resolver dudas de tu tarjeta") { onServicio(ServicioAtencion.LINEA) }
                    HorizontalDivider(color = MoviColors.Divider, modifier = Modifier.padding(horizontal = 14.dp))
                    ServicioRow(Icons.Outlined.ChatBubbleOutline, MoviColors.BlueBg, MoviColors.Blue, "Chat en línea", "Escríbenos y recibe ayuda") { onServicio(ServicioAtencion.CHAT) }
                    HorizontalDivider(color = MoviColors.Divider, modifier = Modifier.padding(horizontal = 14.dp))
                    ServicioRow(Icons.Outlined.Description, MoviColors.YellowBg, MoviColors.YellowText, "Peticiones, quejas y reclamos", "Radica tu solicitud en línea") { onServicio(ServicioAtencion.PQRS) }
                }
            }

            TextButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.AutoMirrored.Outlined.Logout, null, tint = MoviColors.Red, modifier = Modifier.size(18.dp))
                Spacer(Modifier.size(8.dp))
                Text("Cerrar sesión", color = MoviColors.Red, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }

    if (mostrarOpcionesFoto) {
        ModalBottomSheet(
            onDismissRequest = { mostrarOpcionesFoto = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        ) {
            Column(Modifier.padding(horizontal = 20.dp).padding(bottom = 28.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Foto de perfil", color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                OpcionFoto(Icons.Outlined.PhotoCamera, MoviColors.PinkBg, MoviColors.Red, "Tomar foto", "Usa la cámara de tu celular") {
                    mostrarOpcionesFoto = false; onTomarFoto()
                }
                OpcionFoto(Icons.Outlined.PhotoLibrary, MoviColors.YellowBg, MoviColors.YellowText, "Elegir de la galería", "Selecciona una foto que ya tengas") {
                    mostrarOpcionesFoto = false; onElegirGaleria()
                }
                OpcionFoto(Icons.Outlined.DeleteOutline, MoviColors.DangerBg, MoviColors.Danger, "Eliminar foto", "Volver al ícono por defecto", peligro = true) {
                    mostrarOpcionesFoto = false; onEliminarFoto()
                }
                TextButton(onClick = { mostrarOpcionesFoto = false }, modifier = Modifier.fillMaxWidth()) {
                    Text("Cancelar", color = MoviColors.Gray, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (mostrarConfirmarEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmarEliminar = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(28.dp),
            icon = { IconBadge(Icons.Outlined.DeleteOutline, MoviColors.DangerBg, MoviColors.Danger, size = 60.dp) },
            title = { Text("¿Eliminar tarjeta?", color = MoviColors.Navy, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) },
            text = {
                Text(
                    "Dejarás de tener asociada la tarjeta •••• ${state.tarjeta?.ultimos4.orEmpty()}. Podrás agregar otra cuando quieras.",
                    color = MoviColors.Gray, fontSize = 12.sp, textAlign = TextAlign.Center,
                )
            },
            confirmButton = {
                Button(
                    onClick = { mostrarConfirmarEliminar = false; onEliminarTarjeta() },
                    colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Danger),
                    shape = RoundedCornerShape(22.dp),
                ) { Text("Sí, eliminar", fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarConfirmarEliminar = false }, shape = RoundedCornerShape(22.dp)) {
                    Text("Cancelar", color = MoviColors.Gray, fontWeight = FontWeight.Bold)
                }
            },
        )
    }
}

// ==================== COMPONENTES AUXILIARES DE DISEÑO ====================

@Composable
private fun Avatar(foto: Painter?, onCambiar: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier) {
        Box(
            Modifier
                .size(104.dp)
                .shadow(10.dp, CircleShape)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(Color(0xFFFDE3E6), Color(0xFFF7B9C0))))
                .border(4.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (foto != null) {
                Image(foto, "Foto de perfil", Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            } else {
                Icon(Icons.Outlined.Person, "Sin foto", tint = MoviColors.Red, modifier = Modifier.size(52.dp))
            }
        }
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .size(36.dp)
                .shadow(6.dp, CircleShape)
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        colorStops = arrayOf(0f to MoviColors.Yellow, 0.5f to MoviColors.Orange, 1f to MoviColors.Red)
                    )
                )
                .border(3.dp, Color.White, CircleShape)
                .clickable(onClick = onCambiar),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Outlined.PhotoCamera, "Cambiar foto", tint = Color.White, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun Seccion(titulo: String, contenido: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(titulo, color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        contenido()
    }
}

@Composable
private fun Tarjeta(
    padding: Dp = 16.dp,
    spacing: Dp = 14.dp,
    contenido: @Composable ColumnScope.() -> Unit,
) {
    Surface(shape = RoundedCornerShape(28.dp), color = Color.White, shadowElevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = padding), verticalArrangement = Arrangement.spacedBy(spacing), content = contenido)
    }
}

@Composable
private fun SinTarjeta(onAgregar: () -> Unit) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        IconBadge(Icons.Outlined.CreditCard, Color(0xFFECEBF1), Color(0xFF9E9AA6), size = 64.dp)
        Text("No tienes una tarjeta asociada", color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(
            "Agrega tu tarjeta personalizada para redimir puntos y acceder a tus beneficios.",
            color = MoviColors.Gray, fontSize = 12.sp, textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(4.dp))
        GradientButton("Agregar tarjeta", onAgregar, showArrow = false)
    }
}

@Composable
private fun ServicioRow(
    icon: ImageVector, bg: Color, tint: Color, titulo: String, subtitulo: String, onClick: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        IconBadge(icon, bg, tint)
        Column(Modifier.weight(1f)) {
            Text(titulo, color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(subtitulo, color = MoviColors.Gray, fontSize = 11.sp)
        }
        Icon(Icons.Outlined.ChevronRight, null, tint = Color(0xFFB5B1BC), modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun OpcionFoto(
    icon: ImageVector, bg: Color, tint: Color, titulo: String, subtitulo: String,
    peligro: Boolean = false, onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF6F6FA))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        IconBadge(icon, bg, tint, size = 44.dp)
        Column {
            Text(titulo, color = if (peligro) MoviColors.Danger else MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitulo, color = MoviColors.Gray, fontSize = 11.sp)
        }
    }
}

@Composable
private fun IconBadge(icon: ImageVector, backgroundColor: Color, tintColor: Color, size: Dp = 40.dp) {
    Box(
        Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = tintColor, modifier = Modifier.size(size * 0.5f))
    }
}

@Composable
private fun InfoRow(icon: ImageVector, bg: Color, tint: Color, label: String, value: String) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconBadge(icon, bg, tint)
        Column(Modifier.weight(1f)) {
            Text(label, color = MoviColors.Gray, fontSize = 11.sp)
            Text(value, color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

@Composable
private fun StatusChip(text: String, bg: Color, textColor: Color) {
    Box(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = textColor, fontWeight = FontWeight.Bold, fontSize = 10.sp)
    }
}

@Composable
private fun TuLlaveCard(ultimos4: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF1D2A44), Color(0xFF0F172A))))
            .padding(16.dp)
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text("TU LLAVE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text("•••• •••• •••• $ultimos4", color = Color.White, fontSize = 14.sp, letterSpacing = 2.sp)
        }
    }
}

@Composable
private fun GradientButton(text: String, onClick: () -> Unit, showArrow: Boolean = true) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Red),
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier.fillMaxWidth().height(44.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}
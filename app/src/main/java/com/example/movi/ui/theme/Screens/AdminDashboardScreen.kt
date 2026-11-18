package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movi.Data.Model.AdminTab
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.ViewModel.AdminViewModel

@Composable
fun AdminDashboardRoute(
    viewModel: AdminViewModel = viewModel(),
    onCerrarSesion: () -> Unit
) {
    AdminDashboardScreen(
        viewModel = viewModel,
        onCerrarSesion = onCerrarSesion
    )
}

@Composable
fun AdminDashboardScreen(
    viewModel: AdminViewModel,
    onCerrarSesion: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviColors.Background)
    ) {
        // ---- BARRA LATERAL (SIDEBAR) ----
        Column(
            modifier = Modifier
                .width(260.dp)
                .fillMaxHeight()
                .background(MoviColors.Red)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                // Logo y título
                Column {
                    Text("MOVI+", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                    Text("Panel de administración", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                }

                Spacer(Modifier.height(10.dp))

                // Opciones del menú
                AdminMenuOption("Resumen", Icons.Outlined.Dashboard, viewModel.tabSeleccionada == AdminTab.RESUMEN) {
                    viewModel.cambiarTab(AdminTab.RESUMEN)
                }
                AdminMenuOption("Solicitudes de aliados", Icons.Outlined.Storefront, viewModel.tabSeleccionada == AdminTab.SOLICITUDES) {
                    viewModel.cambiarTab(AdminTab.SOLICITUDES)
                }
                AdminMenuOption("Usuarios", Icons.Outlined.People, viewModel.tabSeleccionada == AdminTab.USUARIOS) {
                    viewModel.cambiarTab(AdminTab.USUARIOS)
                }
                AdminMenuOption("Catálogo de ofertas", Icons.Outlined.LocalOffer, viewModel.tabSeleccionada == AdminTab.CATALOGO) {
                    viewModel.cambiarTab(AdminTab.CATALOGO)
                }
                AdminMenuOption("Configuración", Icons.Outlined.Settings, viewModel.tabSeleccionada == AdminTab.CONFIGURACION) {
                    viewModel.cambiarTab(AdminTab.CONFIGURACION)
                }
            }

            // Botón inferior de cerrar sesión
            TextButton(onClick = onCerrarSesion, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Outlined.ExitToApp, null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        // ---- CONTENIDO PRINCIPAL ----
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Encabezado superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (viewModel.tabSeleccionada) {
                        AdminTab.RESUMEN -> "Resumen del sistema"
                        AdminTab.SOLICITUDES -> "Gestión de solicitudes de aliados"
                        AdminTab.USUARIOS -> "Control y gestión de usuarios"
                        AdminTab.CATALOGO -> "Catálogo global de ofertas"
                        AdminTab.CONFIGURACION -> "Configuración del sistema"
                    },
                    color = MoviColors.Navy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text("Administrador MOVI+", color = MoviColors.Gray, fontSize = 12.sp)
            }

            // Tarjetas de Métricas Superiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdminMetricCard("Viajes simulados", viewModel.estadisticas.viajesSimulados, Modifier.weight(1f))
                AdminMetricCard("Puntos emitidos", viewModel.estadisticas.puntosEmitidos, Modifier.weight(1f))
                AdminMetricCard("Canjes realizados", viewModel.estadisticas.canjesRealizados, Modifier.weight(1f))
                AdminMetricCard("Solicitudes pendientes", viewModel.estadisticas.solicitudesPendientes.toString(), Modifier.weight(1f))
            }

            // Secciones del Dashboard principal
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Tarjeta Izquierda: Solicitudes de aliados
                Surface(
                    modifier = Modifier.weight(1.3f).fillMaxHeight(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Solicitudes de aliados", color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 15.sp)

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(viewModel.solicitudesPendientes) { solicitud ->
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MoviColors.Background),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(solicitud.negocio, color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(solicitud.correo, color = MoviColors.Gray, fontSize = 11.sp)
                                            Text(solicitud.categoria, color = MoviColors.Red, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                        }
                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Button(
                                                onClick = { viewModel.aprobarAliado(solicitud.id) },
                                                colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Red),
                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                                modifier = Modifier.height(32.dp)
                                            ) {
                                                Text("Aprobar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                            OutlinedButton(
                                                onClick = { viewModel.rechazarAliado(solicitud.id) },
                                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MoviColors.Danger),
                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                                modifier = Modifier.height(32.dp)
                                            ) {
                                                Text("Rechazar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Tarjeta Derecha: Usuarios
                Surface(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Usuarios", color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 15.sp)

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(viewModel.usuariosSistema) { usuario ->
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MoviColors.Background),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(usuario.nombre, color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text(
                                                text = usuario.estado,
                                                color = if (usuario.activo) Color(0xFF2E7D32) else MoviColors.Danger,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        Button(
                                            onClick = { viewModel.toggleBloqueoUsuario(usuario.id) },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (usuario.activo) MoviColors.Danger else MoviColors.Navy
                                            ),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Text(
                                                text = if (usuario.activo) "Bloquear" else "Desbloquear",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminMenuOption(titulo: String, icon: ImageVector, seleccionado: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (seleccionado) Color.White else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = if (seleccionado) MoviColors.Red else Color.White,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = titulo,
            color = if (seleccionado) MoviColors.Red else Color.White,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun AdminMetricCard(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(titulo, color = MoviColors.Gray, fontSize = 11.sp)
            Text(valor, color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}
package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.ViewModel.CatalogoUiState
import com.example.movi.ui.theme.ViewModel.CatalogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(viewModel: CatalogoViewModel) { // <-- Recibe el ViewModel aquí
    val state = viewModel.uiState // <-- Lee el estado interno del ViewModel

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Recompensas", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MoviColors.Red)
            )
        },
        containerColor = MoviColors.Background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (state) {
                is CatalogoUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MoviColors.Red
                    )
                }

                is CatalogoUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MoviColors.Danger,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is CatalogoUiState.Success -> {
                    val info = state.data
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

                        // Tarjeta de Puntos Acumulados
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Tus Puntos MOVI+",
                                        color = MoviColors.Gray,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${info.puntosDisponibles} Pts",
                                        color = MoviColors.Navy,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Surface(
                                    color = MoviColors.YellowBg,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = MoviColors.Yellow,
                                        modifier = Modifier.padding(12.dp).size(32.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Disponible para Canje",
                            color = MoviColors.Navy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(items = info.recompensasDisponibles) { recompensa ->
                                RecompensaItemCard(
                                    recompensa = recompensa,
                                    onCanjearClick = {
                                        viewModel.seleccionarRecompensa(recompensa)
                                    }
                                )
                            }
                        }

                        // Diálogo de confirmación de canje
                        if (viewModel.recompensaSeleccionada != null) {
                            val item = viewModel.recompensaSeleccionada!!
                            AlertDialog(
                                onDismissRequest = { viewModel.cerrarDialogo() },
                                title = { Text("Confirmar Canje") },
                                text = { Text("¿Deseas canjear '${item.titulo}' por ${item.costoPuntos} puntos?") },
                                confirmButton = {
                                    Button(
                                        onClick = { viewModel.confirmarCanje() },
                                        colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Red)
                                    ) {
                                        Text("Confirmar")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { viewModel.cerrarDialogo() }) {
                                        Text("Cancelar", color = MoviColors.Gray)
                                    }
                                }
                            )
                        }

                        // Alerta si no tiene puntos suficientes
                        if (viewModel.mensajeDialogo != null) {
                            AlertDialog(
                                onDismissRequest = { viewModel.cerrarDialogo() },
                                title = { Text("Puntos Insuficientes") },
                                text = { Text(viewModel.mensajeDialogo!!) },
                                confirmButton = {
                                    Button(
                                        onClick = { viewModel.cerrarDialogo() },
                                        colors = ButtonDefaults.buttonColors(containerColor = MoviColors.Navy)
                                    ) {
                                        Text("Aceptar")
                                    }
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}
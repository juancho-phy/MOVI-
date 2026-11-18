package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movi.Data.Model.PasajeroPuntosUi
import com.example.movi.Data.Model.RecompensaUi
import kotlinx.coroutines.launch

sealed interface CatalogoUiState {
    object Loading : CatalogoUiState
    data class Success(val data: PasajeroPuntosUi) : CatalogoUiState
    data class Error(val message: String) : CatalogoUiState
}

class CatalogoViewModel : ViewModel() {

    var uiState: CatalogoUiState by mutableStateOf(CatalogoUiState.Loading)
        private set

    // Estado para controlar el diálogo de confirmación de canje
    var recompensaSeleccionada: RecompensaUi? by mutableStateOf(null)
        private set

    var mensajeDialogo: String? by mutableStateOf(null)
        private set

    init {
        cargarCatalogoYPuntos()
    }

    fun cargarCatalogoYPuntos() {
        viewModelScope.launch {
            uiState = CatalogoUiState.Loading
            try {
                // TODO: Reemplazar con llamada real a tu repositorio de Spring Boot
                val mockData = PasajeroPuntosUi(
                    nombre = "Camila Rodríguez",
                    puntosDisponibles = 1250,
                    recompensasDisponibles = listOf(
                        RecompensaUi("1", "Viaje Gratis TransMilenio", "Un pasaje sencillo para cualquier troncal", 400, "Transporte"),
                        RecompensaUi("2", "Bono Descuento $5.000", "Válido en locales aliados de AlimentaRed", 600, "Aliados"),
                        RecompensaUi("3", "Recarga Tarjeta tuLlave", "Abono directo de $10.000 a tu tarjeta", 1100, "Transporte"),
                        RecompensaUi("4", "Combo Empanada Nacho", "Canjea una empanada y bebida en local aliado", 350, "Alimentación")
                    )
                )
                uiState = CatalogoUiState.Success(mockData)
            } catch (e: Exception) {
                uiState = CatalogoUiState.Error(e.localizedMessage ?: "Error al cargar catálogo")
            }
        }
    }

    fun seleccionarRecompensa(recompensa: RecompensaUi) {
        val currentState = uiState
        if (currentState is CatalogoUiState.Success) {
            if (currentState.data.puntosDisponibles >= recompensa.costoPuntos) {
                recompensaSeleccionada = recompensa
                mensajeDialogo = null
            } else {
                mensajeDialogo = "No tienes suficientes puntos para canjear '${recompensa.titulo}'."
            }
        }
    }

    fun confirmarCanje() {
        val recompensa = recompensaSeleccionada ?: return
        viewModelScope.launch {
            // TODO: Llamada POST al backend para registrar el canje y descontar puntos
            val currentState = uiState
            if (currentState is CatalogoUiState.Success) {
                val nuevosPuntos = currentState.data.puntosDisponibles - recompensa.costoPuntos
                uiState = CatalogoUiState.Success(currentState.data.copy(puntosDisponibles = nuevosPuntos))
            }
            recompensaSeleccionada = null
        }
    }

    fun cerrarDialogo() {
        recompensaSeleccionada = null
        mensajeDialogo = null
    }
}
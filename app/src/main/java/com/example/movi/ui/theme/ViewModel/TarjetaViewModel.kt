package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movi.Data.Model.EstadoTarjeta
import kotlinx.coroutines.launch

class TarjetaViewModel : ViewModel() {

    var serial by mutableStateOf("")
        private set

    var estado by mutableStateOf(EstadoTarjeta.INGRESANDO)
        private set

    fun actualizarSerial(nuevoSerial: String) {
        serial = nuevoSerial.filter(Char::isDigit).take(16)
        if (estado == EstadoTarjeta.INVALIDA) {
            estado = EstadoTarjeta.INGRESANDO
        }
    }

    fun validarTarjeta() {
        viewModelScope.launch {
            // Lógica de validación con el backend (Spring Boot)
            if (serial.length == 16) {
                // Simulación de respuesta exitosa o inválida
                estado = if (serial.startsWith("4521")) {
                    EstadoTarjeta.GUARDADA
                } else {
                    EstadoTarjeta.INVALIDA
                }
            }
        }
    }

    fun reiniciarValidacion() {
        serial = ""
        estado = EstadoTarjeta.INGRESANDO
    }
}
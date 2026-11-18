package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movi.Data.Model.DashboardUsuarioUi
import com.example.movi.Data.Model.TipoCuenta

class DashboardViewModel : ViewModel() {

    // Estado observable del dashboard con valores iniciales
    var uiState by mutableStateOf(
        DashboardUsuarioUi(
            nombre = "Camila",
            tipoCuenta = TipoCuenta.INSTITUCIONAL // Por defecto institucional
        )
    )
        private set

    // Actualiza el nombre y el tipo de cuenta al iniciar sesión o cambiar de perfil
    fun configurarSesion(nombre: String, tipo: TipoCuenta) {
        uiState = DashboardUsuarioUi(
            nombre = nombre,
            tipoCuenta = tipo
        )
    }

    // Alterna o cambia directamente el tipo de cuenta (útil para pruebas o cambio dinámico)
    fun cambiarTipoCuenta(nuevoTipo: TipoCuenta) {
        uiState = uiState.copy(tipoCuenta = nuevoTipo)
    }
}
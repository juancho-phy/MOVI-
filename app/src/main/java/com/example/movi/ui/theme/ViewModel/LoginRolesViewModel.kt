package com.example.movi.ui.theme.ViewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class RolUsuario {
    PASAJERO,
    ALIADO,
    ADMINISTRADOR
}

class LoginRolesViewModel : ViewModel() {

    var rolSeleccionado by mutableStateOf(RolUsuario.PASAJERO)
        private set

    fun seleccionarRol(
        rol: RolUsuario,
        onNavegarLogin: (RolUsuario) -> Unit
    ) {
        rolSeleccionado = rol
        onNavegarLogin(rol)
    }
}
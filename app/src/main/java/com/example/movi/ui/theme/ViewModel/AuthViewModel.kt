package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movi.data.repository.AuthRepository
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userRole: String = "PASAJERO"
)

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            uiState = uiState.copy(errorMessage = "Completa todos los campos")
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            repository.login(email, password)
                .onSuccess { auth ->
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        userRole = auth.rol ?: "PASAJERO"
                    )
                }
                .onFailure { err ->
                    uiState = uiState.copy(isLoading = false, errorMessage = err.message)
                }
        }
    }

    fun register(nombre: String, email: String, password: String, confirmarPassword: String) {
        if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
            uiState = uiState.copy(errorMessage = "Todos los campos son obligatorios")
            return
        }
        if (password != confirmarPassword) {
            uiState = uiState.copy(errorMessage = "Las contraseñas no coinciden")
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            repository.register(nombre, email, password)
                .onSuccess { auth ->
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        userRole = auth.rol ?: "PASAJERO"
                    )
                }
                .onFailure { err ->
                    uiState = uiState.copy(isLoading = false, errorMessage = err.message)
                }
        }
    }

    fun resetState() {
        uiState = AuthUiState()
    }
}

package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movi.Data.Model.Repository.AuthRepository
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userRole: String = "PASAJERO"
)

class AuthViewModel(
    private val repository:AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            uiState = uiState.copy(
                isSuccess = false,
                errorMessage = "Completa todos los campos"
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                isSuccess = false,
                errorMessage = null
            )

            repository.login(email, password)
                .onSuccess { auth ->
                    val rol = if (!auth.rol.isNullOrBlank()) {
                        auth.rol.uppercase().trim()
                    } else {
                        "PASAJERO"
                    }
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        userRole = rol
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = error.message
                    )
                }
        }
    }

    fun register(
        nombre: String,
        email: String,
        password: String,
        confirmarPassword: String
    ) {
        if (
            nombre.isBlank() ||
            email.isBlank() ||
            password.isBlank() ||
            confirmarPassword.isBlank()
        ) {
            uiState = uiState.copy(
                isSuccess = false,
                errorMessage = "Todos los campos son obligatorios"
            )
            return
        }

        if (password != confirmarPassword) {
            uiState = uiState.copy(
                isSuccess = false,
                errorMessage = "Las contraseñas no coinciden"
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                isSuccess = false,
                errorMessage = null
            )

            repository.register(nombre, email, password)
                .onSuccess { auth ->
                    val rol = if (!auth.rol.isNullOrBlank()) {
                        auth.rol.uppercase().trim()
                    } else {
                        "PASAJERO"
                    }
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        userRole = rol
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = error.message
                    )
                }
        }
    }

    fun resetState() {
        uiState = AuthUiState()
    }

    companion object {
        fun provideFactory(
            repository: AuthRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                        return AuthViewModel(repository) as T
                    }

                    throw IllegalArgumentException(
                        "ViewModel desconocido: ${modelClass.name}"
                    )
                }
            }
    }
}
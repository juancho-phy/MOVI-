
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
    private val repository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    // NORMALIZAR ROL
    private fun normalizarRol(rol: String?): String {
        return rol
            ?.uppercase()
            ?.removePrefix("ROLE_")
            ?.takeIf {
                it in listOf(
                    "PASAJERO",
                    "ALIADO",
                    "ADMINISTRADOR"
                )
            } ?: "PASAJERO"
    }

    // INICIAR SESIÓN
    fun login(
        email: String,
        password: String
    ) {

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

            repository.login(
                email.trim(),
                password
            )
                .onSuccess { auth ->

                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        userRole = normalizarRol(auth.rol)
                    )
                }
                .onFailure { error ->

                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = error.message
                            ?: "Error al iniciar sesión"
                    )
                }
        }
    }

    // REGISTRAR PASAJERO
    fun register(
        nombre: String,
        email: String,
        password: String,
        confirmarPassword: String,
        tipoDocumento: String,
        documentoIdentidad: String
    ) {

        // VALIDAR CAMPOS
        if (
            nombre.isBlank() ||
            email.isBlank() ||
            password.isBlank() ||
            confirmarPassword.isBlank() ||
            tipoDocumento.isBlank() ||
            documentoIdentidad.isBlank()
        ) {
            uiState = uiState.copy(
                isSuccess = false,
                errorMessage = "Todos los campos son obligatorios"
            )
            return
        }

        // VALIDAR CORREO
        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email.trim())
                .matches()
        ) {
            uiState = uiState.copy(
                isSuccess = false,
                errorMessage = "Ingresa un correo electrónico válido"
            )
            return
        }

        // VALIDAR CONTRASEÑA
        if (password.length < 6) {
            uiState = uiState.copy(
                isSuccess = false,
                errorMessage = "La contraseña debe tener mínimo 6 caracteres"
            )
            return
        }

        // CONFIRMAR CONTRASEÑA
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

            repository.register(
                nombre.trim(),
                email.trim(),
                password,
                tipoDocumento,
                documentoIdentidad.trim()
            )
                .onSuccess { auth ->

                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        userRole = normalizarRol(auth.rol)
                    )
                }
                .onFailure { error ->

                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = error.message
                            ?: "Error al registrar el usuario"
                    )
                }
        }
    }

    // REINICIAR ESTADO
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

                    if (
                        modelClass.isAssignableFrom(
                            AuthViewModel::class.java
                        )
                    ) {
                        return AuthViewModel(repository) as T
                    }

                    throw IllegalArgumentException(
                        "ViewModel desconocido: ${modelClass.name}"
                    )
                }
            }
    }
}

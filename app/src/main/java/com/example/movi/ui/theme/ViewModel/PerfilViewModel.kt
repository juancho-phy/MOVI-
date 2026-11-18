package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movi.Data.Model.PerfilUi
import com.example.movi.Data.Model.Repository.PerfilRepository
import com.example.movi.Data.Model.TarjetaUi
import kotlinx.coroutines.launch

// Estados de la UI para el Perfil
sealed interface PerfilUiState {
    object Loading : PerfilUiState
    data class Success(val perfil: PerfilUi) : PerfilUiState
    data class Error(val message: String) : PerfilUiState
}

class PerfilViewModel(private val repository: PerfilRepository) : ViewModel() {

    var uiState: PerfilUiState by mutableStateOf(PerfilUiState.Loading)
        private set

    init {
        cargarPerfil()
    }

    fun cargarPerfil() {
        viewModelScope.launch {
            uiState = PerfilUiState.Loading
            try {
                val response = repository.getPerfil()
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val perfilReal = PerfilUi(
                        nombre = data.nombre,
                        tipoCuenta = data.tipoCuenta,
                        correo = data.correo,
                        identificacion = data.identificacion,
                        tarjeta = data.tarjeta?.let { TarjetaUi(it.ultimos4) }
                    )
                    uiState = PerfilUiState.Success(perfilReal)
                } else {
                    uiState = PerfilUiState.Error("No se pudo cargar la información del perfil")
                }
            } catch (e: Exception) {
                uiState = PerfilUiState.Error(e.localizedMessage ?: "Error de conexión")
            }
        }
    }

    fun tomarFoto() {
        // Lógica para abrir la cámara o procesar la imagen
    }

    fun elegirGaleria() {
        // Lógica para abrir el selector de imágenes de la galería
    }

    fun eliminarFoto() {
        // Lógica para eliminar la foto de perfil en el backend
    }

    fun eliminarTarjeta() {
        viewModelScope.launch {
            try {
                val response = repository.deleteTarjeta()
                if (response.isSuccessful) {
                    cargarPerfil() // Recargar datos tras eliminar la tarjeta
                }
            } catch (e: Exception) {
                // Manejar error de red
            }
        }
    }

    fun cambiarTarjeta() {
        // Lógica para cambiar de tarjeta
    }

    fun agregarTarjeta() {
        // Lógica para registrar nueva tarjeta
    }

    fun cerrarSesion(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.logout()
            } catch (_: Exception) {
                // Incluso si falla la red, limpiamos la sesión local
            } finally {
                onLogoutComplete()
            }
        }
    }

    // Estados de la UI para el Perfil
    sealed interface PerfilUiState {
        object Loading : PerfilUiState
        data class Success(val perfil: PerfilUi) : PerfilUiState
        data class Error(val message: String) : PerfilUiState
    }

    class PerfilViewModel(private val repository: PerfilRepository) : ViewModel() {

        var uiState: PerfilUiState by mutableStateOf(PerfilUiState.Loading)
            private set

        init {
            cargarPerfil()
        }

        fun cargarPerfil() {
            viewModelScope.launch {
                uiState = PerfilUiState.Loading
                try {
                    val response = repository.getPerfil()
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()!!
                        val perfilReal = PerfilUi(
                            nombre = data.nombre,
                            tipoCuenta = data.tipoCuenta,
                            correo = data.correo,
                            identificacion = data.identificacion,
                            tarjeta = data.tarjeta?.let { TarjetaUi(it.ultimos4) }
                        )
                        uiState = PerfilUiState.Success(perfilReal)
                    } else {
                        uiState = PerfilUiState.Error("No se pudo cargar la información del perfil")
                    }
                } catch (e: Exception) {
                    uiState = PerfilUiState.Error(e.localizedMessage ?: "Error de conexión")
                }
            }
        }

        fun tomarFoto() {
            // Lógica para abrir la cámara o procesar la imagen
        }

        fun elegirGaleria() {
            // Lógica para abrir el selector de imágenes de la galería
        }

        fun eliminarFoto() {
            // Lógica para eliminar la foto de perfil en el backend
        }

        fun eliminarTarjeta() {
            viewModelScope.launch {
                try {
                    val response = repository.deleteTarjeta()
                    if (response.isSuccessful) {
                        cargarPerfil() // Recargar datos tras eliminar la tarjeta
                    }
                } catch (e: Exception) {
                    // Manejar error de red
                }
            }
        }

        fun cambiarTarjeta() {
            // Lógica para cambiar de tarjeta
        }

        fun agregarTarjeta() {
            // Lógica para registrar nueva tarjeta
        }

        fun cerrarSesion(onLogoutComplete: () -> Unit) {
            viewModelScope.launch {
                try {
                    repository.logout()
                } catch (_: Exception) {
                    // Incluso si falla la red, limpiamos la sesión local
                } finally {
                    onLogoutComplete()
                }
            }
        }

        // Fábrica tradicional para instanciar el ViewModel pasando el repositorio
        companion object {
            fun provideFactory(repository: PerfilRepository): ViewModelProvider.Factory =
                object : ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return PerfilViewModel(repository) as T
                    }
                }
        }   }
}



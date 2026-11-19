package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import com.example.movi.Data.Model.AdminEstadisticasUi
import com.example.movi.Data.Model.AdminTab
import com.example.movi.Data.Model.SolicitudAliadoUi
import com.example.movi.Data.Model.UsuarioAdminUi
import com.example.movi.Data.Model.Repository.AdminRepository
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: AdminRepository
) : ViewModel() {

    var tabSeleccionada by mutableStateOf(AdminTab.RESUMEN)
        private set

    var estadisticas by mutableStateOf(AdminEstadisticasUi())
        private set

    var solicitudesPendientes by mutableStateOf<List<SolicitudAliadoUi>>(emptyList())
        private set

    var usuariosSistema by mutableStateOf<List<UsuarioAdminUi>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var mensajeExito by mutableStateOf<String?>(null)
        private set

    init {
        cargarEstadisticas()
    }

    fun cambiarTab(nuevaTab: AdminTab) {
        tabSeleccionada = nuevaTab
    }

    fun cargarEstadisticas() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            repository.obtenerEstadisticasAdmin()
                .onSuccess { resultado ->
                    estadisticas = resultado
                }
                .onFailure { error ->
                    errorMessage = error.message
                }

            isLoading = false
        }
    }

    fun aprobarAliado(id: String) {
        viewModelScope.launch {
            errorMessage = null
            mensajeExito = null

            repository.aprobarAliado(id)
                .onSuccess {
                    mensajeExito = "Aliado aprobado correctamente"
                    solicitudesPendientes =
                        solicitudesPendientes.filterNot { it.id == id }
                    cargarEstadisticas()
                }
                .onFailure {
                    errorMessage = it.message
                }
        }
    }

    fun rechazarAliado(id: String) {
        viewModelScope.launch {
            errorMessage = null
            mensajeExito = null

            repository.rechazarAliado(id)
                .onSuccess {
                    mensajeExito = "Solicitud rechazada correctamente"
                    solicitudesPendientes =
                        solicitudesPendientes.filterNot { it.id == id }
                    cargarEstadisticas()
                }
                .onFailure {
                    errorMessage = it.message
                }
        }
    }

    fun toggleBloqueoUsuario(id: String) {
        viewModelScope.launch {
            errorMessage = null
            mensajeExito = null

            repository.toggleBloqueoUsuario(id)
                .onSuccess {
                    usuariosSistema = usuariosSistema.map { usuario ->
                        if (usuario.id == id) {
                            val nuevoActivo = !usuario.activo
                            usuario.copy(
                                activo = nuevoActivo,
                                estado = if (nuevoActivo) {
                                    "Activo"
                                } else {
                                    "Bloqueado manualmente"
                                }
                            )
                        } else {
                            usuario
                        }
                    }

                    mensajeExito = "Estado del usuario actualizado"
                }
                .onFailure {
                    errorMessage = it.message
                }
        }
    }

    companion object {
        fun provideFactory(
            repository: AdminRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
                        return AdminViewModel(repository) as T
                    }
                    throw IllegalArgumentException("ViewModel desconocido")
                }
            }
    }
}
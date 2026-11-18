package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movi.Data.Model.AdminEstadisticasUi
import com.example.movi.Data.Model.AdminTab
import com.example.movi.Data.Model.SolicitudAliadoUi
import com.example.movi.Data.Model.UsuarioAdminUi



class AdminViewModel : ViewModel() {

    var tabSeleccionada by mutableStateOf(AdminTab.RESUMEN)
        private set

    var estadisticas by mutableStateOf(AdminEstadisticasUi())
        private set

    var solicitudesPendientes by mutableStateOf(
        listOf(
            SolicitudAliadoUi("1", "Café Sabana", "contacto@cafesabana.com", "Café y comidas", "Calle 9"),
            SolicitudAliadoUi("2", "FitZone Gym", "admin@fitzone.co", "Salud y deporte", "Avenida Principal"),
            SolicitudAliadoUi("3", "Cine Centro", "ventas@cinecentro.com", "Entretenimiento", "Centro Comercial")
        )
    )
        private set

    var usuariosSistema by mutableStateOf(
        listOf(
            UsuarioAdminUi("1", "Camila Torres", "Activo", true),
            UsuarioAdminUi("2", "Carlos Rojas", "Bloqueado: actividad sospechosa", false),
            UsuarioAdminUi("3", "Laura Gómez", "Activo", true)
        )
    )
        private set

    fun cambiarTab(nuevaTab: AdminTab) {
        tabSeleccionada = nuevaTab
    }

    fun aprobarAliado(id: String) {
        solicitudesPendientes = solicitudesPendientes.filter { it.id != id }
    }

    fun rechazarAliado(id: String) {
        solicitudesPendientes = solicitudesPendientes.filter { it.id != id }
    }

    fun toggleBloqueoUsuario(id: String) {
        usuariosSistema = usuariosSistema.map { usuario ->
            if (usuario.id == id) {
                val nuevoActivo = !usuario.activo
                usuario.copy(
                    activo = nuevoActivo,
                    estado = if (nuevoActivo) "Activo" else "Bloqueado manualmente"
                )
            } else usuario
        }
    }
}

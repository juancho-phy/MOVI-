package com.example.movi.ui.theme.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.movi.Data.Model.AliadoUi
import com.example.movi.Data.Model.CategoriaBeneficio
import com.example.movi.Data.Model.BeneficiosUi
import com.example.movi.Data.Model.Util.PdfReportUtil
import java.io.File


class BeneficiosViewModel : ViewModel() {

    // Datos de ejemplo o listos para ser inyectados desde un Repositorio/API
    private val aliadosDemo = listOf(
        AliadoUi("H&M", CategoriaBeneficio.ROPA, "Bono de descuento", 200, Color(0xFFE50010)),
        AliadoUi("Koaj", CategoriaBeneficio.ROPA, "Descuento en prendas", 150, Color(0xFF111111)),
        AliadoUi("KFC", CategoriaBeneficio.COMIDA, "Combo con descuento", 120, Color(0xFFE4002B)),
        AliadoUi("El Corral", CategoriaBeneficio.COMIDA, "Hamburguesa con descuento", 130, Color(0xFF7A1F1F)),
        AliadoUi("Cine Colombia", CategoriaBeneficio.ENTRETENIMIENTO, "Entrada con descuento", 180, Color(0xFF1B2A6B)),
        AliadoUi("Cinemark", CategoriaBeneficio.ENTRETENIMIENTO, "Boleta con descuento", 170, Color(0xFFC8102E)),
    )

    var uiState by mutableStateOf(
        BeneficiosUi(
            puntosDisponibles = 500,
            esEstudiante = true,
            aliados = aliadosDemo
        )
    )
        private set

    // Estado del filtro de categoría seleccionado
    var categoriaSeleccionada by mutableStateOf<CategoriaBeneficio?>(null)
        private set

    // Aliados filtrados reactivamente
    val aliadosVisibles: List<AliadoUi>
        get() = if (categoriaSeleccionada == null) {
            uiState.aliados
        } else {
            uiState.aliados.filter { it.categoria == categoriaSeleccionada }
        }

    fun seleccionarCategoria(categoria: CategoriaBeneficio?) {
        categoriaSeleccionada = categoria
    }

    fun actualizarPuntos(nuevosPuntos: Int) {
        uiState = uiState.copy(puntosDisponibles = nuevosPuntos)
    }

    fun canjearAliado(context: android.content.Context, aliado: AliadoUi, onPdfReady: (File?) -> Unit) {
        if (uiState.puntosDisponibles >= aliado.puntos) {
            // Descontar puntos
            val nuevosPuntos = uiState.puntosDisponibles - aliado.puntos
            uiState = uiState.copy(puntosDisponibles = nuevosPuntos)

            // Generar reporte PDF del canje
            val archivoPdf = PdfReportUtil.generarComprobanteCanje(context, aliado, uiState.puntosDisponibles + aliado.puntos)
            onPdfReady(archivoPdf)
        }
    }
}
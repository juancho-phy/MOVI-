package com.example.movi.ui.theme.ViewModel

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movi.Data.Model.DashboardAliadoState
import com.example.movi.Data.Model.Remote.AliadoApiService
import com.example.movi.Data.Model.Remote.RetrofitClient
import com.example.movi.Data.Model.Repository.DashboardAliadoRepository
import com.example.movi.Data.Model.Repository.DashboardAliadoRepositoryImpl
import com.example.movi.ui.theme.Screens.DashboardAliadoScreen
import kotlinx.coroutines.launch

class DashboardAliadoViewModel(
    private val repository: DashboardAliadoRepository
) : ViewModel() {

    var uiState by mutableStateOf<DashboardAliadoState>(
        DashboardAliadoState.Loading
    )
        private set

    init {
        cargarDashboard()
    }

    fun cargarDashboard() {
        viewModelScope.launch {

            uiState = DashboardAliadoState.Loading

            try {
                val resultado = repository.obtenerDashboard()

                uiState = DashboardAliadoState.Success(resultado)

            } catch (e: Exception) {

                uiState = DashboardAliadoState.Error(
                    e.localizedMessage ?: "No se pudo cargar el dashboard"
                )
            }
        }
    }

    companion object {

        fun provideFactory(
            repository: DashboardAliadoRepository
        ): ViewModelProvider.Factory {

            return object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return DashboardAliadoViewModel(repository) as T
                }
            }
        }

        @Composable
        fun DashboardAliadoRoute(
            onMisProductos: () -> Unit,
            onAgregarProducto: () -> Unit,
            onValidarCanje: () -> Unit,
            onEditarPuntos: () -> Unit,
            onAvatarClick: () -> Unit,
            onTabSelected: (Int) -> Unit
        ) {
            val context = LocalContext.current

            val apiService = remember {
                RetrofitClient.getInstance(context)
                    .create(AliadoApiService::class.java)
            }

            val repository = remember {
                DashboardAliadoRepositoryImpl(apiService)
            }

            val viewModel: DashboardAliadoViewModel = viewModel(
                factory = DashboardAliadoViewModel.provideFactory(repository)
            )

            when (val state = viewModel.uiState) {
                DashboardAliadoState.Loading -> {
                    CircularProgressIndicator()
                }

                is DashboardAliadoState.Error -> {
                    Text(text = state.mensaje)
                }

                is DashboardAliadoState.Success -> {
                    DashboardAliadoScreen(
                        state = state.dashboard,
                        onMisProductos = onMisProductos,
                        onAgregarProducto = onAgregarProducto,
                        onValidarCanje = onValidarCanje,
                        onEditarPuntos = onEditarPuntos,
                        onAvatarClick = onAvatarClick,
                        onTabSelected = onTabSelected
                    )
                }
            }
        }
    }
}
package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movi.Data.Model.DashboardAliadoState
import com.example.movi.Data.Model.DashboardAliadoUi
import com.example.movi.Data.Model.NavItem
import com.example.movi.Data.Model.Remote.AliadoApiService
import com.example.movi.Data.Model.Remote.RetrofitClient
import com.example.movi.Data.Model.Repository.DashboardAliadoRepositoryImpl
import com.example.movi.ui.theme.Components.IconBadge
import com.example.movi.ui.theme.Components.MoviHeader
import com.example.movi.ui.theme.Components.MoviNavBar
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.MoviTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.movi.ui.theme.ViewModel.DashboardAliadoViewModel

private val AliadoTabs = listOf(
    NavItem("Inicio", Icons.Outlined.Home),
    NavItem("Productos", Icons.Outlined.Inventory2),
    NavItem("Canjes", Icons.Outlined.QrCodeScanner),
    NavItem("Perfil", Icons.Outlined.Person)
)

@Composable
fun DashboardAliadoScreen(
    state: DashboardAliadoUi,
    onMisProductos: () -> Unit,
    onAgregarProducto: () -> Unit,
    onValidarCanje: () -> Unit,
    onEditarPuntos: () -> Unit,
    onAvatarClick: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviColors.Background)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                MoviHeader(
                    saludo = "Hola, ${state.negocio}",
                    avatarIcon = Icons.Outlined.Storefront,
                    chipIcon = Icons.Outlined.Storefront,
                    chipTexto = "${state.categoria} · Aliado",
                    onAvatarClick = onAvatarClick
                )

                Surface(
                    modifier = Modifier
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 176.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {

                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .padding(
                                vertical = 16.dp,
                                horizontal = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Stat(
                            state.productos.toString(),
                            "Productos",
                            Modifier.weight(1f)
                        )

                        Box(
                            Modifier
                                .width(1.dp)
                                .height(36.dp)
                                .background(Color(0xFFE4E2EA))
                        )

                        Stat(
                            state.pendientes.toString(),
                            "Pendientes",
                            Modifier.weight(1f)
                        )

                        Box(
                            Modifier
                                .width(1.dp)
                                .height(36.dp)
                                .background(Color(0xFFE4E2EA))
                        )

                        Stat(
                            state.canjesHoy.toString(),
                            "Canjes hoy",
                            Modifier.weight(1f)
                        )
                    }
                }
            }

            @Composable
            fun DashboardAliadoRoute(
                onMisProductos: () -> Unit,
                onAgregarProducto: () -> Unit,
                onValidarCanje: () -> Unit,
                onEditarPuntos: () -> Unit,
                onAvatarClick: () -> Unit = {},
                onTabSelected: (Int) -> Unit = {}
            ) {
                val context = LocalContext.current

                // Crear el servicio Retrofit
                val apiService = remember {
                    RetrofitClient.getInstance(context)
                        .create(AliadoApiService::class.java)
                }

                // Crear el repositorio
                val repository = remember {
                    DashboardAliadoRepositoryImpl(apiService)
                }

                // Crear el ViewModel
                val viewModel: DashboardAliadoViewModel = viewModel(
                    factory = DashboardAliadoViewModel.provideFactory(repository)
                )

                // Mostrar el estado correspondiente
                when (val state = viewModel.uiState) {

                    DashboardAliadoState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is DashboardAliadoState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = state.mensaje)

                                androidx.compose.material3.Button(
                                    onClick = { viewModel.cargarDashboard() }
                                ) {
                                    Text("Reintentar")
                                }
                            }
                        }
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

            Spacer(Modifier.height(24.dp))

            Text(
                text = "¿Qué quieres hacer?",
                color = MoviColors.Navy,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 14.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Opcion(
                        icon = Icons.Outlined.Inventory2,
                        bg = MoviColors.PinkBg,
                        tint = MoviColors.Red,
                        titulo = "Mis productos",
                        descripcion = "Ve y gestiona lo que ofreces",
                        onClick = onMisProductos,
                        modifier = Modifier.weight(1f)
                    )

                    Opcion(
                        icon = Icons.Outlined.Add,
                        bg = MoviColors.GreenBg,
                        tint = MoviColors.Green,
                        titulo = "Agregar producto",
                        descripcion = "Publica uno nuevo para validación",
                        onClick = onAgregarProducto,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Opcion(
                        icon = Icons.Outlined.QrCodeScanner,
                        bg = MoviColors.YellowBg,
                        tint = MoviColors.YellowText,
                        titulo = "Validar canje",
                        descripcion = "Confirma el código del cliente",
                        onClick = onValidarCanje,
                        modifier = Modifier.weight(1f)
                    )

                    Opcion(
                        icon = Icons.Outlined.MonetizationOn,
                        bg = MoviColors.BlueBg,
                        tint = MoviColors.Blue,
                        titulo = "Editar puntos",
                        descripcion = "Cambia lo que vale cada producto",
                        onClick = onEditarPuntos,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        MoviNavBar(
            items = AliadoTabs,
            selectedIndex = 0,
            onSelect = onTabSelected
        )
    }
}

@Composable
private fun Stat(
    valor: String,
    etiqueta: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        Text(
            text = valor,
            color = MoviColors.Red,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp
        )

        Text(
            text = etiqueta,
            color = MoviColors.Gray,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun Opcion(
    icon: ImageVector,
    bg: Color,
    tint: Color,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            IconBadge(
                icon,
                bg,
                tint,
                size = 46.dp
            )

            Text(
                text = titulo,
                color = MoviColors.Navy,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = descripcion,
                color = MoviColors.Gray,
                fontSize = 11.sp
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 780, widthDp = 390)
@Composable
private fun DashboardAliadoScreenPreview() {
    MoviTheme {
        DashboardAliadoScreen(
            state = DashboardAliadoUi(
                negocio = "Café Central",
                categoria = "Gastronomía",
                productos = 15,
                pendientes = 3,
                canjesHoy = 12
            ),
            onMisProductos = {},
            onAgregarProducto = {},
            onValidarCanje = {},
            onEditarPuntos = {},
            onAvatarClick = {},
            onTabSelected = {}
        )
    }
}
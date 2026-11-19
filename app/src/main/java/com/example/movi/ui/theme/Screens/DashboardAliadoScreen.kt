
package com.example.movi.ui.theme.Screens

import android.R.attr.icon
import android.R.attr.tint
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.example.movi.ui.theme.ViewModel.DashboardAliadoViewModel

private val AliadoTabs = listOf(
    NavItem("Inicio", Icons.Outlined.Home),
    NavItem("Productos", Icons.Outlined.Inventory2),
    NavItem("Canjes", Icons.Outlined.QrCodeScanner),
    NavItem("Perfil", Icons.Outlined.Person)
)

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MoviColors.Bg),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MoviColors.Red
                )
            }
        }

        is DashboardAliadoState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MoviColors.Bg)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "No pudimos cargar tu negocio",
                        color = MoviColors.Navy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = state.mensaje,
                        color = MoviColors.Gray,
                        fontSize = 13.sp
                    )

                    Button(
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
            .background(MoviColors.Bg)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Encabezado y resumen
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
                                vertical = 18.dp,
                                horizontal = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Stat(
                            valor = state.productos.toString(),
                            etiqueta = "Productos",
                            modifier = Modifier.weight(1f)
                        )

                        SeparadorEstadistica()

                        Stat(
                            valor = state.pendientes.toString(),
                            etiqueta = "Pendientes",
                            modifier = Modifier.weight(1f)
                        )

                        SeparadorEstadistica()

                        Stat(
                            valor = state.canjesHoy.toString(),
                            etiqueta = "Canjes hoy",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Acciones
            Text(
                text = "¿Qué quieres hacer?",
                color = MoviColors.Navy,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
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

            // Catálogo del aliado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 4.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tus productos",
                    color = MoviColors.Navy,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Ver todos",
                    color = MoviColors.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onMisProductos()
                    }
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
                    .clickable {
                        onMisProductos()
                    },
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    IconBadge(
                        icon = Icons.Outlined.Inventory2,
                        backgroundColor = MoviColors.PinkBg,
                        tintColor = MoviColors.Red,
                        size = 46.dp
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${state.productos} productos registrados",
                            color = MoviColors.Navy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Text(
                            text = "Administra tu catálogo y sus beneficios",
                            color = MoviColors.Gray,
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        text = "›",
                        color = MoviColors.GrayLight,
                        fontSize = 26.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        MoviNavBar(
            items = AliadoTabs,
            selectedIndex = 0,
            onSelect = onTabSelected
        )
    }
}

@Composable
private fun SeparadorEstadistica() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(36.dp)
            .background(MoviColors.Divider)
    )
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
        verticalArrangement = Arrangement.spacedBy(3.dp)
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
            fontSize = 10.sp
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
                icon = Icons.Outlined.Inventory2,
                backgroundColor = MoviColors.PinkBg,
                tintColor = MoviColors.Red,
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

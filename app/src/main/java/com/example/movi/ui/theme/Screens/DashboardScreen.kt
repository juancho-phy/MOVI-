
package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movi.Data.Model.DashboardUsuarioUi
import com.example.movi.Data.Model.MoviTab
import com.example.movi.Data.Model.TipoCuenta
import com.example.movi.ui.theme.Components.GradientButton
import com.example.movi.ui.theme.Components.IconBadge
import com.example.movi.ui.theme.Components.MoviBottomBar
import com.example.movi.ui.theme.Components.MoviHeader
import com.example.movi.ui.theme.Components.StatusChip
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.ViewModel.DashboardViewModel

private data class MarcaAliada(
    val nombre: String,
    val iniciales: String,
    val color: Color
)

private val marcasAliadas = listOf(
    MarcaAliada("H&M", "H&M", Color(0xFFE50010)),
    MarcaAliada("Koaj", "KOAJ", Color(0xFF111111)),
    MarcaAliada("KFC", "KFC", Color(0xFFE4002B)),
    MarcaAliada("El Corral", "EC", Color(0xFF7A1F1F)),
    MarcaAliada("Cine Colombia", "CC", Color(0xFF1B2A6B)),
    MarcaAliada("Cinemark", "C", Color(0xFFC8102E))
)

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = viewModel(),
    onVerBeneficios: () -> Unit,
    onTransporteClick: () -> Unit,
    onComerciosClick: () -> Unit,
    onAvatarClick: () -> Unit = {},
    onTabSelected: (MoviTab) -> Unit = {}
) {
    DashboardUsuarioScreen(
        state = viewModel.uiState,
        onVerBeneficios = onVerBeneficios,
        onTransporteClick = onTransporteClick,
        onComerciosClick = onComerciosClick,
        onAvatarClick = onAvatarClick,
        onTabSelected = onTabSelected
    )
}

@Composable
fun DashboardUsuarioScreen(
    state: DashboardUsuarioUi,
    onVerBeneficios: () -> Unit,
    onTransporteClick: () -> Unit,
    onComerciosClick: () -> Unit,
    onAvatarClick: () -> Unit = {},
    onTabSelected: (MoviTab) -> Unit = {}
) {
    val institucional = state.tipoCuenta == TipoCuenta.INSTITUCIONAL

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
            Box(modifier = Modifier.fillMaxWidth()) {

                MoviHeader(
                    saludo = "Hola, ${state.nombre}",
                    avatarIcon = Icons.Outlined.Person,
                    chipIcon = if (institucional) {
                        Icons.Outlined.School
                    } else {
                        Icons.Outlined.Email
                    },
                    chipTexto = if (institucional) {
                        "Correo institucional · Estudiante"
                    } else {
                        "Correo personal"
                    },
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
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (institucional) {
                                "Tus beneficios estudiantiles"
                            } else {
                                "Tus beneficios"
                            },
                            color = MoviColors.Navy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = if (institucional) {
                                "Transporte con tarifa estudiantil y descuentos comerciales"
                            } else {
                                "Descuentos en comercios aliados"
                            },
                            color = MoviColors.Gray,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        GradientButton(
                            "Ver mis beneficios",
                            onVerBeneficios
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Marcas aliadas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Marcas aliadas",
                    color = MoviColors.Navy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Text(
                    text = "Ver todas",
                    color = MoviColors.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        onComerciosClick()
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 14.dp,
                        bottom = 22.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                marcasAliadas.forEach { marca ->
                    MarcaAliadaCard(
                        marca = marca,
                        onClick = onComerciosClick
                    )
                }
            }

            // Beneficios disponibles
            Text(
                text = "Beneficios disponibles",
                color = MoviColors.Navy,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (institucional) {
                    BeneficioItem(
                        icon = Icons.Outlined.DirectionsBus,
                        iconBg = MoviColors.YellowBg,
                        iconTint = MoviColors.YellowText,
                        titulo = "Transporte estudiantil",
                        descripcion = "Tarifa preferencial en todas las rutas",
                        trailing = {
                            StatusChip(
                                text = "Activo",
                                bg = MoviColors.GreenBg,
                                textColor = MoviColors.Green
                            )
                        },
                        onClick = onTransporteClick
                    )

                    BeneficioItem(
                        icon = Icons.Outlined.Storefront,
                        iconBg = MoviColors.PinkBg,
                        iconTint = MoviColors.Red,
                        titulo = "Comercios aliados",
                        descripcion = "Descuentos en comida, tiendas y más",
                        trailing = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BrandMiniStack()
                                Chevron()
                            }
                        },
                        onClick = onComerciosClick
                    )
                } else {
                    BeneficioItem(
                        icon = Icons.Outlined.Storefront,
                        iconBg = MoviColors.PinkBg,
                        iconTint = MoviColors.Red,
                        titulo = "Comercios aliados",
                        descripcion = "Descuentos en comida, tiendas y más",
                        trailing = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BrandMiniStack()
                                Chevron()
                            }
                        },
                        onClick = onComerciosClick
                    )

                    BeneficioItem(
                        icon = Icons.Outlined.Lock,
                        iconBg = Color(0xFFECEBF1),
                        iconTint = Color(0xFF9E9AA6),
                        titulo = "Transporte estudiantil",
                        descripcion = "Solo con correo institucional",
                        trailing = {
                            StatusChip(
                                text = "Bloqueado",
                                bg = Color(0xFFECEBF1),
                                textColor = MoviColors.Gray
                            )
                        },
                        bloqueado = true,
                        onClick = {}
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        MoviBottomBar(
            selected = MoviTab.INICIO,
            onSelect = onTabSelected
        )
    }
}

@Composable
private fun MarcaAliadaCard(
    marca: MarcaAliada,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(108.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 12.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(marca.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = marca.iniciales,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
            }

            Text(
                text = marca.nombre,
                color = MoviColors.Navy,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun BrandMiniStack() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        marcasAliadas.take(3).forEachIndexed { index, marca ->
            Box(
                modifier = Modifier
                    .offset(x = (-index * 7).dp)
                    .size(23.dp)
                    .clip(CircleShape)
                    .background(marca.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = marca.iniciales.take(1),
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun Chevron() {
    Icon(
        imageVector = Icons.Outlined.ChevronRight,
        contentDescription = "Ver más",
        tint = Color(0xFFB5B1BC),
        modifier = Modifier.size(20.dp)
    )
}

@Composable
private fun BeneficioItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    titulo: String,
    descripcion: String,
    trailing: @Composable () -> Unit,
    bloqueado: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = !bloqueado,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconBadge(
                icon,
                iconBg,
                iconTint,
                size = 46.dp
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    color = if (bloqueado) {
                        MoviColors.Gray
                    } else {
                        MoviColors.Navy
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = descripcion,
                    color = MoviColors.Gray,
                    fontSize = 11.sp
                )
            }

            trailing()
        }
    }
}

@Preview(showBackground = true, heightDp = 850, widthDp = 390)
@Composable
private fun DashboardInstitucionalPreview() {
    DashboardUsuarioScreen(
        state = DashboardUsuarioUi(
            "Camila",
            TipoCuenta.INSTITUCIONAL
        ),
        onVerBeneficios = {},
        onTransporteClick = {},
        onComerciosClick = {}
    )
}

@Preview(showBackground = true, heightDp = 850, widthDp = 390)
@Composable
private fun DashboardPersonalPreview() {
    DashboardUsuarioScreen(
        state = DashboardUsuarioUi(
            "Camila",
            TipoCuenta.PERSONAL
        ),
        onVerBeneficios = {},
        onTransporteClick = {},
        onComerciosClick = {}
    )
}

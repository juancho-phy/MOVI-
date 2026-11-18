package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movi.Data.Model.AliadoUi
import com.example.movi.Data.Model.CategoriaBeneficio
import com.example.movi.Data.Model.BeneficiosUi
import com.example.movi.Data.Model.MoviTab
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.Components.MoviBottomBar
import com.example.movi.ui.theme.ViewModel.BeneficiosViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.example.movi.ui.theme.Components.IconBadge
import com.example.movi.ui.theme.Components.StatusChip


@Composable
fun BeneficiosRoute(
    viewModel: BeneficiosViewModel = viewModel(),
    onAliadoClick: (AliadoUi) -> Unit,
    onTransporteClick: () -> Unit,
    onTabSelected: (MoviTab) -> Unit = {},
) {
    val context = LocalContext.current

    BeneficiosScreen(
        state = viewModel.uiState,
        categoriaSeleccionada = viewModel.categoriaSeleccionada,
        aliadosVisibles = viewModel.aliadosVisibles,
        onCategoriaSelected = { viewModel.seleccionarCategoria(it) },
        onAliadoClick = { aliado ->
            // Canjea el beneficio y genera automáticamente el comprobante PDF
            viewModel.canjearAliado(context, aliado) { archivoPdf ->
                // Opcional: Aquí puedes manejar el archivo generado (ej. abrirlo con un Intent)
            }
        },
        onTransporteClick = onTransporteClick,
        onTabSelected = onTabSelected
    )
}

@Composable
fun BeneficiosScreen(
    state: BeneficiosUi,
    categoriaSeleccionada: CategoriaBeneficio?,
    aliadosVisibles: List<AliadoUi>,
    onCategoriaSelected: (CategoriaBeneficio?) -> Unit,
    onAliadoClick: (AliadoUi) -> Unit,
    onTransporteClick: () -> Unit,
    onTabSelected: (MoviTab) -> Unit = {},
) {
    Column(Modifier.fillMaxSize().background(MoviColors.Background)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) { Encabezado(state.puntosDisponibles) }

            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item { FiltroChip("Todos", categoriaSeleccionada == null) { onCategoriaSelected(null) } }
                    items(CategoriaBeneficio.values().toList()) { c ->
                        FiltroChip(c.etiqueta, categoriaSeleccionada == c) { onCategoriaSelected(c) }
                    }
                }
            }

            if (state.esEstudiante) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    TransporteCard(Modifier.padding(horizontal = 24.dp), onTransporteClick)
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "Marcas aliadas", color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }

            itemsIndexed(aliadosVisibles) { i, aliado ->
                AliadoCard(
                    aliado = aliado,
                    onClick = { onAliadoClick(aliado) },
                    modifier = Modifier.padding(start = if (i % 2 == 0) 24.dp else 0.dp, end = if (i % 2 == 0) 0.dp else 24.dp),
                )
            }
        }
        MoviBottomBar(selected = MoviTab.BENEFICIOS, onSelect = onTabSelected)
    }
}

@Composable
fun Encabezado(puntos: Int) {
    Box(Modifier.fillMaxWidth().height(230.dp)) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(Brush.linearGradient(listOf(MoviColors.Red, MoviColors.RedLight)))
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text("Beneficios", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text("Canjea tus puntos en marcas aliadas", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
        }
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 24.dp).fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 8.dp,
        ) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IconBadge(Icons.Outlined.Stars, MoviColors.YellowBg, MoviColors.YellowText, size = 46.dp)
                Column(Modifier.weight(1f)) {
                    Text("Tus puntos", color = MoviColors.GrayLight, fontSize = 11.sp)
                    Text("$puntos pts", color = MoviColors.Red, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                }
                StatusChip("Disponibles", MoviColors.GreenBg, MoviColors.Green)
            }
        }
    }
}

@Composable
private fun FiltroChip(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    val forma = RoundedCornerShape(16.dp)
    Box(
        Modifier
            .clip(forma)
            .background(if (seleccionado) MoviColors.Red else Color.White)
            .then(if (seleccionado) Modifier else Modifier.border(1.dp, Color(0xFFE4E2EA), forma))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp),
    ) {
        Text(
            texto,
            color = if (seleccionado) Color.White else MoviColors.Gray,
            fontWeight = FontWeight.Bold, fontSize = 12.sp,
        )
    }
}

@Composable
fun TransporteCard(modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 4.dp,
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconBadge(Icons.Outlined.DirectionsBus, MoviColors.YellowBg, MoviColors.YellowText, size = 46.dp)
            Column(Modifier.weight(1f)) {
                Text("Transporte estudiantil", color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Tarifa preferencial en todas las rutas", color = MoviColors.Gray, fontSize = 11.sp)
            }
            StatusChip("Activo", MoviColors.GreenBg, MoviColors.Green)
        }
    }
}

@Composable
fun AliadoCard(aliado: AliadoUi, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 4.dp,
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                Modifier.fillMaxWidth().height(84.dp).clip(RoundedCornerShape(16.dp)).background(aliado.colorMarca),
                contentAlignment = Alignment.Center,
            ) {
                if (aliado.logo != null) {
                    Image(aliado.logo, aliado.nombre, Modifier.padding(16.dp), contentScale = ContentScale.Fit)
                } else {
                    Text(
                        aliado.nombre.uppercase(),
                        color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp,
                        textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 8.dp),
                    )
                }
            }
            Text(aliado.nombre, color = MoviColors.Navy, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(aliado.oferta, color = MoviColors.Gray, fontSize = 11.sp)
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                StatusChip(aliado.categoria.etiqueta, MoviColors.PinkBg, MoviColors.Red)
                Text("${aliado.puntos} pts", color = MoviColors.Red, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900, widthDp = 390)
@Composable
private fun BeneficiosEstudiantePreview() {
    val aliadosDemo = listOf(
        AliadoUi("H&M", CategoriaBeneficio.ROPA, "Bono de descuento", 200, Color(0xFFE50010)),
        AliadoUi("Koaj", CategoriaBeneficio.ROPA, "Descuento en prendas", 150, Color(0xFF111111)),
        AliadoUi("KFC", CategoriaBeneficio.COMIDA, "Combo con descuento", 120, Color(0xFFE4002B)),
        AliadoUi("El Corral", CategoriaBeneficio.COMIDA, "Hamburguesa con descuento", 130, Color(0xFF7A1F1F)),
        AliadoUi("Cine Colombia", CategoriaBeneficio.ENTRETENIMIENTO, "Entrada con descuento", 180, Color(0xFF1B2A6B)),
        AliadoUi("Cinemark", CategoriaBeneficio.ENTRETENIMIENTO, "Boleta con descuento", 170, Color(0xFFC8102E)),
    )
    BeneficiosScreen(
        state = BeneficiosUi(500, esEstudiante = true, aliados = aliadosDemo),
        categoriaSeleccionada = null,
        aliadosVisibles = aliadosDemo,
        onCategoriaSelected = {},
        onAliadoClick = {},
        onTransporteClick = {}
    )
}

@Preview(showBackground = true, heightDp = 900, widthDp = 390)
@Composable
private fun BeneficiosPersonalPreview() {
    val aliadosDemo = listOf(
        AliadoUi("H&M", CategoriaBeneficio.ROPA, "Bono de descuento", 200, Color(0xFFE50010)),
        AliadoUi("Koaj", CategoriaBeneficio.ROPA, "Descuento en prendas", 150, Color(0xFF111111)),
        AliadoUi("KFC", CategoriaBeneficio.COMIDA, "Combo con descuento", 120, Color(0xFFE4002B)),
        AliadoUi("El Corral", CategoriaBeneficio.COMIDA, "Hamburguesa con descuento", 130, Color(0xFF7A1F1F)),
        AliadoUi("Cine Colombia", CategoriaBeneficio.ENTRETENIMIENTO, "Entrada con descuento", 180, Color(0xFF1B2A6B)),
        AliadoUi("Cinemark", CategoriaBeneficio.ENTRETENIMIENTO, "Boleta con descuento", 170, Color(0xFFC8102E)),
    )
    BeneficiosScreen(
        state = BeneficiosUi(500, esEstudiante = false, aliados = aliadosDemo),
        categoriaSeleccionada = null,
        aliadosVisibles = aliadosDemo,
        onCategoriaSelected = {},
        onAliadoClick = {},
        onTransporteClick = {}
    )
}
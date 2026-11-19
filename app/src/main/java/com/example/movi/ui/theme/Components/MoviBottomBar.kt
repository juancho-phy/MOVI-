package com.example.movi.ui.theme.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.Data.Model.MoviTab
import com.example.movi.ui.theme.MoviColors

private val visibleTabs = listOf(MoviTab.INICIO, MoviTab.TARJETA, MoviTab.BENEFICIOS, MoviTab.PERFIL)

private fun MoviTab.toIcon(): ImageVector = when (this) {
    MoviTab.INICIO -> Icons.Outlined.Home
    MoviTab.TARJETA -> Icons.Outlined.CreditCard
    MoviTab.BENEFICIOS -> Icons.Outlined.CardGiftcard
    MoviTab.PERFIL -> Icons.Outlined.Person
    else -> Icons.Outlined.Home
}

private fun MoviTab.toLabel(): String = when (this) {
    MoviTab.INICIO -> "Inicio"
    MoviTab.TARJETA -> "Tarjeta"
    MoviTab.BENEFICIOS -> "Beneficios"
    MoviTab.PERFIL -> "Perfil"
    else -> "Inicio"
}

@Composable
fun MoviBottomBar(
    selected: MoviTab,
    onSelect: (MoviTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
        color = Color.White,
        shadowElevation = 12.dp,
    ) {
        Row(
            Modifier.fillMaxWidth().navigationBarsPadding()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            visibleTabs.forEach { tab ->
                val on = tab == selected
                val color = if (on) MoviColors.Red else Color(0xFF9E9AA6)
                val icon = tab.toIcon()
                val label = tab.toLabel()

                Column(
                    Modifier.clickable { onSelect(tab) }.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Icon(icon, label, tint = color, modifier = Modifier.size(24.dp))
                    Text(
                        label,
                        color = color,
                        fontSize = 10.sp,
                        fontWeight = if (on) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

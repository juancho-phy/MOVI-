package com.example.movi.ui.theme.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.movi.Data.Model.NavItem
import com.example.movi.ui.theme.MoviColors
import kotlin.collections.forEachIndexed

@Composable
fun MoviNavBar(
    items: List<NavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, item ->

            val seleccionado = selectedIndex == index

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onSelect(index)
                    }
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = item.icono,
                    contentDescription = item.titulo,
                    tint = if (seleccionado)
                        MoviColors.Red
                    else
                        MoviColors.Gray,
                    modifier = Modifier.size(23.dp)
                )

                Text(
                    text = item.titulo,
                    color = if (seleccionado)
                        MoviColors.Red
                    else
                        MoviColors.Gray,
                    fontSize = 11.sp,
                    fontWeight = if (seleccionado)
                        FontWeight.Bold
                    else
                        FontWeight.Normal
                )
            }
        }
    }
}
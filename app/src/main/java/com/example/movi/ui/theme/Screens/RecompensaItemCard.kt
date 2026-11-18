package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.Data.Model.RecompensaUi
import com.example.movi.ui.theme.MoviColors

@Composable
fun RecompensaItemCard(
    recompensa: RecompensaUi,
    onCanjearClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = recompensa.titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MoviColors.Navy
                )

                Text(
                    text = recompensa.descripcion,
                    fontSize = 13.sp,
                    color = MoviColors.Gray
                )

                Text(
                    text = "${recompensa.costoPuntos} puntos",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MoviColors.Red
                )
            }

            Button(
                onClick = onCanjearClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoviColors.Red
                )
            ) {
                Text("Canjear")
            }
        }
    }
}
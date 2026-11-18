package com.example.movi.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movi.ui.theme.MoviColors
import com.example.movi.ui.theme.ViewModel.LoginRolesViewModel
import com.example.movi.ui.theme.ViewModel.RolUsuario

@Composable
fun LoginRolesRoute(
    viewModel: LoginRolesViewModel = viewModel(),
    onNavegarDashboard: (RolUsuario) -> Unit
) {
    LoginRolesScreen(
        onRolSelected = { rol ->
            viewModel.seleccionarRol(rol, onNavegarDashboard)
        }
    )
}

@Composable
fun LoginRolesScreen(
    onRolSelected: (RolUsuario) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MoviColors.Red, MoviColors.RedLight, MoviColors.Background))),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "MOVI+",
                    color = MoviColors.Red,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp
                )
                Text(
                    text = "Selecciona tu perfil de ingreso",
                    color = MoviColors.Gray,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(8.dp))

                // Botón 1: Pasajero / Usuario
                RolButton(
                    texto = "Iniciar como Pasajero",
                    icon = Icons.Outlined.Person,
                    containerColor = MoviColors.Red,
                    onClick = { onRolSelected(RolUsuario.PASAJERO) }
                )

                // Botón 2: Aliado
                RolButton(
                    texto = "Iniciar como Aliado",
                    icon = Icons.Outlined.Storefront,
                    containerColor = MoviColors.Red,
                    onClick = { onRolSelected(RolUsuario.ALIADO) }
                )

                // Botón 3: Administrador
                RolButton(
                    texto = "Iniciar como Administrador",
                    icon = Icons.Outlined.AdminPanelSettings,
                    containerColor = MoviColors.Red,
                    onClick = { onRolSelected(RolUsuario.ADMINISTRADOR) }
                )
            }
        }
    }
}

@Composable
private fun RolButton(
    texto: String,
    icon: ImageVector,
    containerColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(22.dp))
            Text(texto, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Preview(showBackground = true, heightDp = 700, widthDp = 390)
@Composable
private fun LoginRolesPreview() {
    LoginRolesScreen(onRolSelected = {})
}
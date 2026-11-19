package com.example.movi.ui.theme.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TuLlaveCard(serialLast: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1B1B3A),
        shadowElevation = 4.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "tuLlave · $serialLast",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showArrow: Boolean = false
) {
    MoviPillButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        arrow = if (showArrow) PillArrow.Inline else PillArrow.None
    )
}

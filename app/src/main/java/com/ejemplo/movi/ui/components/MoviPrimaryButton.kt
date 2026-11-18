package com.ejemplo.movi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.ui.theme.ButtonGradient
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviRed
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.OnPrimary

@Composable
fun MoviPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showArrow: Boolean = false,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.buttonHeight)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.extraLarge,
                spotColor = MoviRed,
                ambientColor = MoviRed,
            )
            .clip(MaterialTheme.shapes.extraLarge)
            .background(brush = Brush.horizontalGradient(ButtonGradient))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = OnPrimary,
            )
            if (showArrow) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = OnPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = Dimens.spaceXL),
                )
            }
        }
    }
}

@Preview
@Composable
private fun MoviPrimaryButtonPreview() {
    MoviTheme {
        MoviPrimaryButton(
            text = "Ingresar",
            onClick = {},
            showArrow = true,
        )
    }
}

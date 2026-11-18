package com.ejemplo.movi.ui.screens.dashboard.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.NeutralBadge
import com.ejemplo.movi.ui.theme.NeutralBadgeContainer
import com.ejemplo.movi.ui.theme.Success
import com.ejemplo.movi.ui.theme.SuccessContainer

enum class BadgeStyle {
    Success,
    Neutral,
}

@Composable
fun StatusBadge(
    text: String,
    style: BadgeStyle,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = when (style) {
            BadgeStyle.Success -> SuccessContainer
            BadgeStyle.Neutral -> NeutralBadgeContainer
        },
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = when (style) {
                BadgeStyle.Success -> Success
                BadgeStyle.Neutral -> NeutralBadge
            },
            modifier = Modifier.padding(
                horizontal = Dimens.spaceS,
                vertical = Dimens.spaceXS,
            ),
        )
    }
}

@Preview
@Composable
private fun StatusBadgePreview() {
    MoviTheme {
        StatusBadge(
            text = "Activo",
            style = BadgeStyle.Success,
        )
    }
}

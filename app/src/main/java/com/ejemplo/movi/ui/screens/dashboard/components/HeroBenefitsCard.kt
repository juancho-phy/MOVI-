package com.ejemplo.movi.ui.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.ui.components.MoviPrimaryButton
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.TextPrimary
import com.ejemplo.movi.ui.theme.TextSecondary

@Composable
fun HeroBenefitsCard(
    title: String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimens.heroCardRadius),
        color = Color.White,
        shadowElevation = 12.dp,
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spaceL),
            verticalArrangement = Arrangement.spacedBy(Dimens.spaceS),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
            )
            Spacer(modifier = Modifier.height(Dimens.spaceXS))
            MoviPrimaryButton(
                text = buttonText,
                onClick = onButtonClick,
                showArrow = true,
            )
        }
    }
}

@Preview
@Composable
private fun HeroBenefitsCardPreview() {
    MoviTheme {
        HeroBenefitsCard(
            title = "Tus beneficios estudiantiles",
            subtitle = "Transporte con tarifa estudiantil y descuentos comerciales",
            buttonText = "Ver mis beneficios",
            onButtonClick = {},
        )
    }
}

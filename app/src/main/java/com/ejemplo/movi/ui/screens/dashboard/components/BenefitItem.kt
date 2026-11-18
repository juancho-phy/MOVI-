package com.ejemplo.movi.ui.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.ui.screens.dashboard.model.BenefitIcon
import com.ejemplo.movi.ui.screens.dashboard.model.BenefitStatus
import com.ejemplo.movi.ui.screens.dashboard.model.BenefitUi
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.IconTintGray
import com.ejemplo.movi.ui.theme.IconTintGrayBg
import com.ejemplo.movi.ui.theme.IconTintPink
import com.ejemplo.movi.ui.theme.IconTintPinkBg
import com.ejemplo.movi.ui.theme.IconTintYellow
import com.ejemplo.movi.ui.theme.IconTintYellowBg
import com.ejemplo.movi.ui.theme.InputIcon
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.TextPrimary
import com.ejemplo.movi.ui.theme.TextSecondary

@Composable
fun BenefitItem(
    benefit: BenefitUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (benefit.status != BenefitStatus.Blocked) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            ),
        shape = RoundedCornerShape(Dimens.itemCardRadius),
        color = Color.White,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.padding(Dimens.spaceM),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM),
        ) {
            val (bgColor, iconVector, tintColor) = when {
                benefit.status == BenefitStatus.Blocked -> Triple(
                    IconTintGrayBg,
                    Icons.Outlined.Lock,
                    IconTintGray,
                )
                benefit.icon == BenefitIcon.Transport -> Triple(
                    IconTintYellowBg,
                    Icons.Outlined.DirectionsBus,
                    IconTintYellow,
                )
                else -> Triple(
                    IconTintPinkBg,
                    Icons.Outlined.Storefront,
                    IconTintPink,
                )
            }

            Box(
                modifier = Modifier
                    .size(Dimens.iconBoxSize)
                    .background(
                        color = bgColor,
                        shape = RoundedCornerShape(Dimens.iconBoxRadius),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = tintColor,
                    modifier = Modifier.size(Dimens.navIconSize),
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = benefit.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                )
                Text(
                    text = benefit.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
            }

            when (benefit.status) {
                BenefitStatus.Active -> StatusBadge(
                    text = benefit.statusLabel.orEmpty(),
                    style = BadgeStyle.Success,
                )
                BenefitStatus.Blocked -> StatusBadge(
                    text = benefit.statusLabel.orEmpty(),
                    style = BadgeStyle.Neutral,
                )
                BenefitStatus.Navigable -> Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = InputIcon,
                )
            }
        }
    }
}

@Preview
@Composable
private fun BenefitItemPreview() {
    MoviTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.screenHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.spaceS),
        ) {
            BenefitItem(
                benefit = BenefitUi(
                    id = "1",
                    title = "Transporte estudiantil",
                    subtitle = "Tarifa preferencial en todas las rutas",
                    icon = BenefitIcon.Transport,
                    status = BenefitStatus.Active,
                    statusLabel = "Activo",
                ),
            )
            BenefitItem(
                benefit = BenefitUi(
                    id = "2",
                    title = "Comercios aliados",
                    subtitle = "Descuentos en comida, tiendas y más",
                    icon = BenefitIcon.Stores,
                    status = BenefitStatus.Navigable,
                ),
            )
            BenefitItem(
                benefit = BenefitUi(
                    id = "3",
                    title = "Transporte estudiantil",
                    subtitle = "Solo con correo institucional",
                    icon = BenefitIcon.Transport,
                    status = BenefitStatus.Blocked,
                    statusLabel = "Bloqueado",
                ),
            )
        }
    }
}

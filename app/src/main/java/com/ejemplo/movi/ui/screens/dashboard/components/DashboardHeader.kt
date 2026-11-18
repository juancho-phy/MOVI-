package com.ejemplo.movi.ui.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.R
import com.ejemplo.movi.ui.screens.dashboard.model.AccountChipIcon
import com.ejemplo.movi.ui.theme.DashboardHeaderGradient
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.MoviYellow

@Composable
fun DashboardHeader(
    userName: String,
    accountChipText: String,
    accountChipIcon: AccountChipIcon,
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit = {},
) {
    val headerShape = RoundedCornerShape(
        bottomStart = Dimens.dashboardHeaderBottomRadius,
        bottomEnd = Dimens.dashboardHeaderBottomRadius,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(headerShape)
            .background(brush = Brush.verticalGradient(DashboardHeaderGradient))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(
                top = Dimens.spaceM,
                start = Dimens.screenHorizontalPadding,
                end = Dimens.screenHorizontalPadding,
                bottom = Dimens.heroOverlap + Dimens.spaceL,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val annotatedLogo = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("MOVI")
                }
                withStyle(style = SpanStyle(color = MoviYellow)) {
                    append("+")
                }
            }
            Text(
                text = annotatedLogo,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
            )

            Box(
                modifier = Modifier
                    .size(Dimens.avatarSize)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f))
                    .clickable(onClick = onAvatarClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.spaceL))

        Text(
            text = stringResource(R.string.dashboard_greeting, userName),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(Dimens.spaceS))

        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .background(Color.White.copy(alpha = 0.25f))
                .padding(
                    horizontal = Dimens.spaceM,
                    vertical = Dimens.spaceXS,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val chipIcon = when (accountChipIcon) {
                AccountChipIcon.Institutional -> Icons.Outlined.School
                AccountChipIcon.Personal -> Icons.Outlined.Email
            }
            Icon(
                imageVector = chipIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp),
            )
            Spacer(modifier = Modifier.width(Dimens.spaceXS))
            Text(
                text = accountChipText,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun DashboardHeaderPreview() {
    MoviTheme {
        DashboardHeader(
            userName = "Camila",
            accountChipText = "Correo institucional · Estudiante",
            accountChipIcon = AccountChipIcon.Institutional,
        )
    }
}

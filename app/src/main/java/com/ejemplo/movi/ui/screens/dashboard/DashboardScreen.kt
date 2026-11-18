package com.ejemplo.movi.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ejemplo.movi.R
import com.ejemplo.movi.ui.preview.DashboardPreviewData
import com.ejemplo.movi.ui.screens.dashboard.components.BenefitItem
import com.ejemplo.movi.ui.screens.dashboard.components.DashboardHeader
import com.ejemplo.movi.ui.screens.dashboard.components.HeroBenefitsCard
import com.ejemplo.movi.ui.screens.dashboard.components.MoviBottomBar
import com.ejemplo.movi.ui.screens.dashboard.components.NavTab
import com.ejemplo.movi.ui.screens.dashboard.model.BenefitUi
import com.ejemplo.movi.ui.screens.dashboard.model.DashboardUiState
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.TextPrimary

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    modifier: Modifier = Modifier,
    onHeroClick: () -> Unit = {},
    onBenefitClick: (BenefitUi) -> Unit = {},
) {
    var selectedTab by rememberSaveable { mutableStateOf(NavTab.Home) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            DashboardHeader(
                userName = state.userName,
                accountChipText = state.accountChipText,
                accountChipIcon = state.accountChipIcon,
            )

            Column(
                modifier = Modifier
                    .offset(y = -Dimens.heroOverlap)
                    .padding(horizontal = Dimens.screenHorizontalPadding)
                    .padding(bottom = Dimens.spaceXL),
                verticalArrangement = Arrangement.spacedBy(Dimens.spaceM),
            ) {
                HeroBenefitsCard(
                    title = state.heroTitle,
                    subtitle = state.heroSubtitle,
                    buttonText = state.heroButtonText,
                    onButtonClick = onHeroClick,
                )

                Text(
                    text = stringResource(R.string.dashboard_section_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                )

                state.benefits.forEach { benefit ->
                    BenefitItem(
                        benefit = benefit,
                        onClick = { onBenefitClick(benefit) },
                    )
                }
            }
        }

        MoviBottomBar(
            selected = selectedTab,
            onSelect = { selectedTab = it },
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DashboardScreenInstitutionalPreview() {
    MoviTheme {
        DashboardScreen(state = DashboardPreviewData.institutional)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DashboardScreenPersonalPreview() {
    MoviTheme {
        DashboardScreen(state = DashboardPreviewData.personal)
    }
}

package com.ejemplo.movi.ui.screens.dashboard.model

enum class AccountChipIcon {
    Institutional,
    Personal,
}

enum class BenefitIcon {
    Transport,
    Stores,
}

enum class BenefitStatus {
    Active,
    Blocked,
    Navigable,
}

data class BenefitUi(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: BenefitIcon,
    val status: BenefitStatus,
    val statusLabel: String? = null,
)

data class DashboardUiState(
    val userName: String,
    val accountChipText: String,
    val accountChipIcon: AccountChipIcon,
    val heroTitle: String,
    val heroSubtitle: String,
    val heroButtonText: String,
    val benefits: List<BenefitUi>,
)

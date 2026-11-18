package com.ejemplo.movi.ui.preview

import com.ejemplo.movi.ui.screens.dashboard.model.AccountChipIcon
import com.ejemplo.movi.ui.screens.dashboard.model.BenefitIcon
import com.ejemplo.movi.ui.screens.dashboard.model.BenefitStatus
import com.ejemplo.movi.ui.screens.dashboard.model.BenefitUi
import com.ejemplo.movi.ui.screens.dashboard.model.DashboardUiState

// TEMPORAL: datos de ejemplo, se reemplazan al implementar la lógica real
object DashboardPreviewData {
    val institutional = DashboardUiState(
        userName = "Camila",
        accountChipText = "Correo institucional · Estudiante",
        accountChipIcon = AccountChipIcon.Institutional,
        heroTitle = "Tus beneficios estudiantiles",
        heroSubtitle = "Transporte con tarifa estudiantil y descuentos comerciales",
        heroButtonText = "Ver mis beneficios",
        benefits = listOf(
            BenefitUi(
                id = "transport",
                title = "Transporte estudiantil",
                subtitle = "Tarifa preferencial en todas las rutas",
                icon = BenefitIcon.Transport,
                status = BenefitStatus.Active,
                statusLabel = "Activo",
            ),
            BenefitUi(
                id = "stores",
                title = "Comercios aliados",
                subtitle = "Descuentos en comida, tiendas y más",
                icon = BenefitIcon.Stores,
                status = BenefitStatus.Navigable,
            ),
        ),
    )

    val personal = DashboardUiState(
        userName = "Camila",
        accountChipText = "Correo personal",
        accountChipIcon = AccountChipIcon.Personal,
        heroTitle = "Tus beneficios",
        heroSubtitle = "Descuentos en comercios aliados",
        heroButtonText = "Ver mis beneficios",
        benefits = listOf(
            BenefitUi(
                id = "stores",
                title = "Comercios aliados",
                subtitle = "Descuentos en comida, tiendas y más",
                icon = BenefitIcon.Stores,
                status = BenefitStatus.Navigable,
            ),
            BenefitUi(
                id = "transport",
                title = "Transporte estudiantil",
                subtitle = "Solo con correo institucional",
                icon = BenefitIcon.Transport,
                status = BenefitStatus.Blocked,
                statusLabel = "Bloqueado",
            ),
        ),
    )
}

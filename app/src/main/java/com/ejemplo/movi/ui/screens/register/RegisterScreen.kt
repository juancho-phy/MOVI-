package com.ejemplo.movi.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.R
import com.ejemplo.movi.ui.components.DotsGrid
import com.ejemplo.movi.ui.components.MoviCard
import com.ejemplo.movi.ui.components.MoviCheckboxRow
import com.ejemplo.movi.ui.components.MoviHeader
import com.ejemplo.movi.ui.components.MoviPrimaryButton
import com.ejemplo.movi.ui.components.MoviTextField
import com.ejemplo.movi.ui.components.OrangeRing
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviTheme

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit = {},
) {
    var name by rememberSaveable { mutableStateOf("") }
    var id by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var acceptTerms by rememberSaveable { mutableStateOf(value = false) }
    var acceptData by rememberSaveable { mutableStateOf(value = false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clipToBounds(),
    ) {
        DotsGrid(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Dimens.spaceS, bottom = 96.dp),
        )

        OrangeRing(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 24.dp, y = 24.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            MoviHeader(modifier = Modifier.fillMaxWidth())

            MoviCard(
                modifier = Modifier
                    .padding(horizontal = Dimens.screenHorizontalPadding)
                    .offset(y = -Dimens.cardOverlap),
            ) {
                MoviTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = stringResource(R.string.register_name_placeholder),
                    leadingIcon = Icons.Outlined.Person,
                )

                MoviTextField(
                    value = id,
                    onValueChange = { id = it },
                    placeholder = stringResource(R.string.register_id_placeholder),
                    leadingIcon = Icons.Outlined.Badge,
                )

                MoviTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(R.string.register_password_placeholder),
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                )

                MoviTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = stringResource(R.string.register_confirm_password_placeholder),
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                )

                Column(verticalArrangement = Arrangement.spacedBy(Dimens.spaceS)) {
                    MoviCheckboxRow(
                        checked = acceptTerms,
                        onCheckedChange = { acceptTerms = it },
                        label = stringResource(R.string.register_terms),
                    )

                    MoviCheckboxRow(
                        checked = acceptData,
                        onCheckedChange = { acceptData = it },
                        label = stringResource(R.string.register_data),
                    )
                }

                MoviPrimaryButton(
                    text = stringResource(R.string.register_button),
                    onClick = onRegisterClick,
                    showArrow = true,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    MoviTheme {
        RegisterScreen()
    }
}

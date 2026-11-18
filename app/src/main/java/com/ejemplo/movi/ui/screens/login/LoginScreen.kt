package com.ejemplo.movi.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.ejemplo.movi.ui.theme.LinkRed
import com.ejemplo.movi.ui.theme.MoviTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by rememberSaveable { mutableStateOf(value = false) }

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
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(R.string.login_email_placeholder),
                    leadingIcon = Icons.Outlined.Email,
                )

                MoviTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(R.string.login_password_placeholder),
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MoviCheckboxRow(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        label = stringResource(R.string.login_remember),
                    )

                    Text(
                        text = stringResource(R.string.login_forgot),
                        style = MaterialTheme.typography.labelMedium,
                        color = LinkRed,
                        modifier = Modifier.clickable { onForgotPasswordClick() },
                    )
                }

                MoviPrimaryButton(
                    text = stringResource(R.string.login_button),
                    onClick = onLoginClick,
                    showArrow = false,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    MoviTheme {
        LoginScreen()
    }
}

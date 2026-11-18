package com.ejemplo.movi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviTheme

@Composable
fun MoviCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 12.dp,
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = Dimens.cardHorizontalPadding,
                vertical = Dimens.cardVerticalPadding,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.spaceM),
            content = content,
        )
    }
}

@Preview
@Composable
private fun MoviCardPreview() {
    MoviTheme {
        MoviCard {
            Text(
                text = "Card Content",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

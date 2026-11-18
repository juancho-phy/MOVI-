package com.ejemplo.movi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.InputBorder
import com.ejemplo.movi.ui.theme.MoviRed
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.TextSecondary

@Composable
fun MoviCheckboxRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.checkboxSize)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(if (checked) MoviRed else Color.Transparent)
                .border(
                    width = 1.dp,
                    color = if (checked) MoviRed else InputBorder,
                    shape = MaterialTheme.shapes.extraSmall,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp),
                )
            }
        }
        Spacer(modifier = Modifier.width(Dimens.spaceS))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
        )
    }
}

@Preview
@Composable
private fun MoviCheckboxRowPreview() {
    MoviTheme {
        var checked by remember { mutableStateOf(value = true) }
        MoviCheckboxRow(
            checked = checked,
            onCheckedChange = { checked = it },
            label = "Recordarme",
        )
    }
}

package com.ejemplo.movi.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.MoviOrange
import com.ejemplo.movi.ui.theme.MoviTheme

@Composable
fun DotsGrid(
    modifier: Modifier = Modifier,
) {
    val dotRadiusPx = with(LocalDensity.current) { 2.dp.toPx() }
    val spacingPx = with(LocalDensity.current) { 8.dp.toPx() }
    val columns = 3
    val rows = 5

    val widthDp = (2.dp * 2 * columns) + (8.dp * (columns - 1))
    val heightDp = (2.dp * 2 * rows) + (8.dp * (rows - 1))

    Canvas(modifier = modifier.size(widthDp, heightDp)) {
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                val cx = dotRadiusPx + (col * ((dotRadiusPx * 2) + spacingPx))
                val cy = dotRadiusPx + (row * ((dotRadiusPx * 2) + spacingPx))
                drawCircle(
                    color = MoviOrange,
                    radius = dotRadiusPx,
                    center = Offset(cx, cy),
                )
            }
        }
    }
}

@Composable
fun OrangeRing(
    modifier: Modifier = Modifier,
) {
    val strokeWidthPx = with(LocalDensity.current) { 10.dp.toPx() }
    Canvas(modifier = modifier.size(72.dp)) {
        val diameter = size.minDimension
        drawCircle(
            color = MoviOrange,
            radius = (diameter - strokeWidthPx) / 2f,
            style = Stroke(width = strokeWidthPx),
        )
    }
}

@Preview
@Composable
private fun MoviDecorationsPreview() {
    MoviTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceL)) {
            DotsGrid()
            OrangeRing()
        }
    }
}

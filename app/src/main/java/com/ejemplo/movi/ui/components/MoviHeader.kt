package com.ejemplo.movi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ejemplo.movi.R
import com.ejemplo.movi.ui.theme.Dimens
import com.ejemplo.movi.ui.theme.HeaderGradient
import com.ejemplo.movi.ui.theme.MoviTheme
import com.ejemplo.movi.ui.theme.MoviYellow

@Composable
fun MoviHeader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(HeaderGradient),
                shape = RoundedCornerShape(
                    bottomStart = Dimens.headerBottomCornerRadius,
                    bottomEnd = Dimens.headerBottomCornerRadius,
                ),
            )
            .padding(vertical = Dimens.spaceXXL),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("MOVI")
                }
                withStyle(style = SpanStyle(color = MoviYellow)) {
                    append("+")
                }
            }

            Text(
                text = annotatedText,
                style = MaterialTheme.typography.displayLarge,
            )

            Spacer(modifier = Modifier.height(Dimens.spaceM))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(Dimens.busImageHeight)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(MoviYellow, Color.Transparent),
                        ),
                    ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_bus),
                    contentDescription = null,
                    modifier = Modifier.height(Dimens.busImageHeight),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MoviHeaderPreview() {
    MoviTheme {
        MoviHeader()
    }
}

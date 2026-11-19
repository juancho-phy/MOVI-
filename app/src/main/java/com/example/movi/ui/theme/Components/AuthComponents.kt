package com.example.movi.ui.theme.Components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movi.ui.theme.MoviColors

// 1. Modifier.pullUp
fun Modifier.pullUp(amount: Dp): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val up = amount.roundToPx()
    layout(placeable.width, (placeable.height - up).coerceAtLeast(0)) {
        placeable.place(0, -up)
    }
}

// 2. MoviLogo
@Composable
fun MoviLogo(fontSize: TextUnit, modifier: Modifier = Modifier) {
    val text = buildAnnotatedString {
        withStyle(SpanStyle(color = Color.White)) {
            append("MOVI")
        }
        withStyle(SpanStyle(color = MoviColors.Yellow)) {
            append("+")
        }
    }
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier
    )
}

// 3. AuthHeader
@Composable
fun AuthHeader(
    modifier: Modifier = Modifier,
    @DrawableRes busRes: Int? = null,
    busHeight: Dp = 120.dp,
    bottomPadding: Dp = 72.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 120.dp, bottomEnd = 120.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        MoviColors.AuthHeaderTop,
                        MoviColors.AuthHeaderMid,
                        MoviColors.AuthHeaderBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 28.dp, bottom = bottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MoviLogo(fontSize = 48.sp)
            Spacer(modifier = Modifier.height(16.dp))
            if (busRes != null) {
                Image(
                    painter = painterResource(busRes),
                    contentDescription = null,
                    modifier = Modifier.height(busHeight),
                    contentScale = ContentScale.Fit
                )
            } else {
                MoviBusIllustration(
                    modifier = Modifier.size(width = busHeight * 2.2f, height = busHeight)
                )
            }
        }
    }
}

// 4. MoviBusIllustration
@Composable
fun MoviBusIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Radial yellow glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFD54F), Color.Transparent),
                radius = width * 0.7f,
                center = center
            ),
            radius = width * 0.7f,
            center = center
        )

        // Skyline of 6 rectangles #33B50F22
        val barWidth = width / 12f
        val heights = listOf(0.3f, 0.5f, 0.4f, 0.6f, 0.35f, 0.45f)
        for (i in 0..5) {
            val x = i * (width / 6f) + barWidth * 0.5f
            val h = height * heights[i]
            drawRect(
                color = Color(0x33B50F22),
                topLeft = androidx.compose.ui.geometry.Offset(x, height - h),
                size = androidx.compose.ui.geometry.Size(barWidth, h)
            )
        }

        // Bus body #D7192B with rounded corners 16.dp
        val bodyHeight = height * 0.55f
        val bodyTop = height * 0.25f
        drawRoundRect(
            color = Color(0xFFD7192B),
            topLeft = androidx.compose.ui.geometry.Offset(0f, bodyTop),
            size = androidx.compose.ui.geometry.Size(width, bodyHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx(), 16.dp.toPx())
        )

        // Strip #B50F22
        drawRect(
            color = Color(0xFFB50F22),
            topLeft = androidx.compose.ui.geometry.Offset(0f, bodyTop + bodyHeight * 0.7f),
            size = androidx.compose.ui.geometry.Size(width, bodyHeight * 0.15f)
        )

        // 5 windows #263238
        val windowWidth = width / 7f
        val windowHeight = bodyHeight * 0.35f
        val windowTop = bodyTop + bodyHeight * 0.15f
        for (i in 0..4) {
            val wx = width * 0.08f + i * (windowWidth + width * 0.03f)
            drawRoundRect(
                color = Color(0xFF263238),
                topLeft = androidx.compose.ui.geometry.Offset(wx, windowTop),
                size = androidx.compose.ui.geometry.Size(windowWidth, windowHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
        }

        // 2 wheels (#212121 with center #BDBDBD)
        val wheelRadius = height * 0.12f
        val wheelY = bodyTop + bodyHeight
        val wheel1X = width * 0.25f
        val wheel2X = width * 0.75f

        drawCircle(color = Color(0xFF212121), radius = wheelRadius, center = androidx.compose.ui.geometry.Offset(wheel1X, wheelY))
        drawCircle(color = Color(0xFFBDBDBD), radius = wheelRadius * 0.5f, center = androidx.compose.ui.geometry.Offset(wheel1X, wheelY))

        drawCircle(color = Color(0xFF212121), radius = wheelRadius, center = androidx.compose.ui.geometry.Offset(wheel2X, wheelY))
        drawCircle(color = Color(0xFFBDBDBD), radius = wheelRadius * 0.5f, center = androidx.compose.ui.geometry.Offset(wheel2X, wheelY))
    }
}

// 5. MoviTextField
@Composable
fun MoviTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val borderColor = if (isFocused) MoviColors.Red else MoviColors.Border

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .onFocusChanged { isFocused = it.isFocused }
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp),
        singleLine = true,
        textStyle = TextStyle(color = MoviColors.Ink, fontSize = 14.sp),
        cursorBrush = SolidColor(MoviColors.Red),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = MoviColors.InputIcon,
                    modifier = Modifier.size(20.dp)
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = MoviColors.Muted,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = null,
                            tint = MoviColors.InputIcon,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    )
}

// 6. MoviCheckRow
@Composable
fun MoviCheckRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = if (checked) MoviColors.Red else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (checked) MoviColors.Red else MoviColors.Border,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
        Text(
            text = label,
            color = MoviColors.Muted,
            fontSize = 11.sp
        )
    }
}

// 7. PillArrow & MoviPillButton
enum class PillArrow { None, Inline, End }

@Composable
fun MoviPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    arrow: PillArrow = PillArrow.None
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .shadow(
                elevation = if (enabled) 8.dp else 0.dp,
                shape = RoundedCornerShape(50),
                ambientColor = MoviColors.Red,
                spotColor = MoviColors.Red
            )
            .clickable(enabled = enabled && !loading, onClick = onClick),
        shape = RoundedCornerShape(50),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (enabled) {
                        Brush.horizontalGradient(listOf(MoviColors.Red, MoviColors.RedBright))
                    } else {
                        Brush.linearGradient(listOf(MoviColors.Border, MoviColors.Border))
                    }
                )
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                when (arrow) {
                    PillArrow.None -> {
                        Text(
                            text = text,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    PillArrow.Inline -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = text,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    PillArrow.End -> {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = text,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterEnd)
                            )
                        }
                    }
                }
            }
        }
    }
}

// 8. DotsGrid & OrangeRing
@Composable
fun DotsGrid(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(width = 26.dp, height = 46.dp)) {
        val cols = 3
        val rows = 5
        val radius = 2.dp.toPx()
        val spacing = 10.dp.toPx()
        val startX = radius
        val startY = radius
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val cx = startX + c * spacing
                val cy = startY + r * spacing
                drawCircle(color = MoviColors.Yellow, radius = radius, center = androidx.compose.ui.geometry.Offset(cx, cy))
            }
        }
    }
}

@Composable
fun OrangeRing(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(72.dp)) {
        val stroke = 10.dp.toPx()
        val radius = (size.minDimension - stroke) / 2f
        drawCircle(
            color = MoviColors.Orange,
            radius = radius,
            center = center,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke)
        )
    }
}

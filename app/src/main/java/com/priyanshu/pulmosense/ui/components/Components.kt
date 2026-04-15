package com.priyanshu.pulmosense.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.priyanshu.pulmosense.ui.theme.*

@Composable
fun AmbientMeshBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "mesh")
    val xOffset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(15000, easing = LinearEasing), RepeatMode.Reverse),
        label = "xOffset"
    )
    val yOffset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 500f,
        animationSpec = infiniteRepeatable(tween(18000, easing = LinearEasing), RepeatMode.Reverse),
        label = "yOffset"
    )

    // Permanent Pastel Red Background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PastelRed)
    )

    // Animated Neon Red & Soft Pink blobs
    Canvas(modifier = Modifier
        .fillMaxSize()
        .blur(120.dp)) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(NeonRed.copy(alpha = 0.4f), Color.Transparent),
                center = Offset(size.width / 2 + xOffset / 4, size.height / 3 + yOffset / 3),
                radius = size.width * 0.9f
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(SoftPink.copy(alpha = 0.3f), Color.Transparent),
                center = Offset(size.width - xOffset / 2, size.height / 1.5f - yOffset / 2),
                radius = size.width * 1.1f
            )
        )
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    // Increased opacity for a more solid, premium SaaS feel
    val glassBg = Color.White.copy(alpha = 0.45f)
    val glassBdr = Color.White.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(glassBg)
            .border(1.5.dp, glassBdr, RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val glassBg = Color.White.copy(alpha = 0.45f)
    val glassBdr = Color.White.copy(alpha = 0.3f)

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.DarkGray) },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(glassBg)
            .border(1.5.dp, glassBdr, RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            errorTextColor = DangerRed,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            cursorColor = NeonRed
        ),
        singleLine = true
    )
}

@Composable
fun ShimmerSkeleton(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 0.5f,
        animationSpec = infiniteRepeatable(tween(2000, easing = FastOutLinearInEasing), RepeatMode.Reverse),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = alpha),
                        Color.White.copy(alpha = alpha * 0.5f)
                    )
                )
            )
            .border(1.dp, Color.White.copy(alpha = alpha), RoundedCornerShape(16.dp))
    )
}

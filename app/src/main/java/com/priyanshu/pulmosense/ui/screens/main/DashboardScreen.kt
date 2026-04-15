package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.pulmosense.ui.theme.NeonRed
import com.priyanshu.pulmosense.ui.theme.PastelRed
import com.priyanshu.pulmosense.ui.theme.SoftPink

@Composable
fun DashboardScreen(onNavigateToRecording: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.weight(1f))
        
        BreatheOrb(onClick = onNavigateToRecording)
        
        Spacer(modifier = Modifier.weight(1.5f))
        
        MedicalDisclaimer()
    }
}

@Composable
fun HeaderSection() {
    val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    val greeting = when (currentHour) {
        in 0..11 -> "Good Morning,"
        in 12..16 -> "Good Afternoon,"
        else -> "Good Evening,"
    }
    
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(greeting, color = Color.Black.copy(alpha = 0.6f), fontSize = 18.sp)
        Text("Priyanshu", color = Color.Black, fontSize = 38.sp, fontWeight = FontWeight.ExtraBold)
        Text("Ready for your daily screening?", color = Color.Black.copy(alpha = 0.8f), fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun BreatheOrb(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f, targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(230.dp)
            .clickable { onClick() }
    ) {
        // Outer Neon Glow
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = NeonRed.copy(alpha = glow * 0.4f),
                radius = size.width / 2 * scale
            )
            drawCircle(
                color = NeonRed.copy(alpha = glow * 0.6f),
                radius = (size.width / 2 - 15f) * scale,
                style = Stroke(width = 6f)
            )
        }

        // Inner Solid Core - Brighter Neon look
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(NeonRed, Color(0xFFFF4D4D))
                    )
                )
                .border(3.dp, Color.White.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "START",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                letterSpacing = 3.sp
            )
        }
    }
}

@Composable
fun MedicalDisclaimer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.35f))
            .border(1.5.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Rounded.Info, contentDescription = "Warning", tint = NeonRed, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            "Powered by experimental ML engines. Not a clinical diagnosis. Consult a physician for all medical queries.",
            color = Color.Black.copy(alpha = 0.9f), fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium
        )
    }
}

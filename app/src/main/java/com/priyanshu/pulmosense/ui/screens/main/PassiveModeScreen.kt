package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.pulmosense.ui.theme.*

@Composable
fun PassiveModeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Passive Mode", color = Color.Black.copy(alpha = 0.6f), fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text("Sleep Tracking", color = Color.Black, fontSize = 38.sp, fontWeight = FontWeight.ExtraBold)
            Text("Monitor your breathing patterns overnight silently with the power of PulmoSense AI.", color = Color.Black.copy(alpha = 0.8f), fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        SleepOrb()
        
        Spacer(modifier = Modifier.weight(1.5f))
        
        Text(
            text = "Keeps the microphone active in low-power mode to detect snoring, apnea events, and nocturnal wheezing. All processing stays local.",
            color = Color.Black.copy(alpha = 0.7f),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SleepOrb() {
    var isTracking by remember { mutableStateOf(false) }
    
    val infiniteTransition = rememberInfiniteTransition(label = "sleep_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = if (isTracking) 0.92f else 0.98f, 
        targetValue = if (isTracking) 1.08f else 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isTracking) 2000 else 4000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )
    val glow by infiniteTransition.animateFloat(
        initialValue = if (isTracking) 0.4f else 0.2f, 
        targetValue = if (isTracking) 0.8f else 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isTracking) 2000 else 4000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(240.dp)
            .clickable { isTracking = !isTracking }
    ) {
        // Outer Glow using NeonRed for high energy
        Canvas(modifier = Modifier.fillMaxSize()) {
            val color = if (isTracking) NeonRed else Color.White
            drawCircle(
                color = color.copy(alpha = glow * 0.4f),
                radius = size.width / 2 * scale
            )
            drawCircle(
                color = color.copy(alpha = glow * 0.6f),
                radius = (size.width / 2 - 25f) * scale,
                style = Stroke(width = 4f)
            )
        }

        // Inner Core - Premium Glass look with NeonRed icon
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.45f))
                .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Rounded.NightsStay, 
                    contentDescription = "Sleep", 
                    tint = if (isTracking) NeonRed else Color.Black, 
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    if (isTracking) "ACTIVE" else "START",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    letterSpacing = 3.sp
                )
            }
        }
    }
}

package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.pulmosense.ui.components.GlassCard
import com.priyanshu.pulmosense.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToInstant: () -> Unit,
    onNavigateToPassive: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        
        // Greeting row
        val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val greeting = when (currentHour) {
            in 0..11 -> "Good Morning,"
            in 12..16 -> "Good Afternoon,"
            else -> "Good Evening,"
        }
        Text(
            text = greeting,
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Priyanshu 👋",
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Welcome to PulmoSense",
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 38.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your AI-powered respiratory health companion. Monitor your lungs anytime, anywhere with clinical precision.",
            color = Color.Black.copy(alpha = 0.8f),
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Quick Actions",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ModeCard(
                title = "Instant",
                icon = Icons.Rounded.GraphicEq,
                onClick = onNavigateToInstant,
                modifier = Modifier.weight(1f)
            )
            ModeCard(
                title = "Passive",
                icon = Icons.Rounded.NightsStay,
                onClick = onNavigateToPassive,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "Model Intelligence",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Our core model is trained on over 12,000 clinically validated respiratory sound samples, utilizing a deep convolutional neural network fine-tuned for anomaly detection in breath patterns.",
            color = Color.Black.copy(alpha = 0.75f),
            fontSize = 14.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Metrics Grid
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MetricCard(title = "Accuracy", value = "96.99%", icon = Icons.Rounded.CheckCircle, modifier = Modifier.weight(1f))
            MetricCard(title = "Sensitivity", value = "96.91%", icon = Icons.Rounded.CheckCircle, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MetricCard(title = "Precision", value = "97.11%", icon = Icons.Rounded.CheckCircle, modifier = Modifier.weight(1f))
            MetricCard(title = "F1 Score", value = "97.01%",icon = Icons.Rounded.CheckCircle,  modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(120.dp)) // padding for bottom nav
    }
}

@Composable
fun ModeCard(title: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val glassBg = Color.White.copy(alpha = 0.45f)
    val glassBdr = Color.White.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .height(115.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(glassBg)
            .border(1.5.dp, glassBdr, RoundedCornerShape(24.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon, 
                contentDescription = title, 
                modifier = Modifier.size(34.dp),
                tint = NeonRed
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.Black)
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier.height(105.dp)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, color = NeonRed, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = title, color = Color.Black.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

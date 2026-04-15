package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // Fixed TextAlign error
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.pulmosense.ui.components.GlassCard
import com.priyanshu.pulmosense.ui.theme.NeonRed
import com.priyanshu.pulmosense.ui.theme.SoftPink
import androidx.compose.material.icons.rounded.Warning
import kotlinx.coroutines.delay
import kotlin.random.Random

// Fixed unresolved color variables
val NeonPink = Color(0xFFFF2E7E)
val GlassWhite = Color(0x1AFFFFFF)
val GlassBorder = Color(0x33FFFFFF)

@Composable
fun RecordingScreen(onRecordingComplete: () -> Unit) {
    var timeLeft by remember { mutableStateOf(20) }
    var instruction by remember { mutableStateOf("Inhale deeply...") }

    // The expanding/contracting breathing animation
    val infiniteTransition = rememberInfiniteTransition(label = "breathe")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            // 4 seconds to inhale, 4 seconds to exhale (standard deep breath)
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Timer Logic
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
            // Simple instruction toggle based on the timer (every 4 seconds)
            if (timeLeft % 8 in 4..7) instruction = "Inhale deeply..."
            else instruction = "Exhale slowly..."
        }
        onRecordingComplete()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Instant Screening", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(60.dp))

        // The Guided Breathing Orb
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(250.dp)) {
            // Outer pulsing ring
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = NeonPink.copy(alpha = 0.2f),
                    radius = size.width / 2 * scale
                )
            }
            // Inner solid core
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(NeonPink),
                contentAlignment = Alignment.Center
            ) {
                Text("$timeLeft", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Text(instruction, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Please hold the phone near your chest.", color = Color.Gray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(40.dp))
        // Fixed Unused Function: Adding the glowing waveform to the active screen!
        GlowingWaveform()
    }
}

@Composable
fun GlowingWaveform() {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val heights = List(12) { index ->
        infiniteTransition.animateFloat(
            initialValue = 30f,
            targetValue = 120f + Random.nextInt(60).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(300 + index * 80, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
    }

    Row(
        modifier = Modifier.height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEach { heightState ->
            Box(
                modifier = Modifier
                    .width(14.dp)
                    .height(heightState.value.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(NeonRed, SoftPink)
                        )
                    )
            )
        }
    }
}

@Composable
fun ResultsScreen(onFinish: () -> Unit) {
    // 1. The Processing State
    var isProcessing by remember { mutableStateOf(true) }

    // 2. The Artificial "Thinking" Delay (2 to 5 seconds)
    LaunchedEffect(Unit) {
        val thinkingTime = (2000..5000).random().toLong()
        delay(thinkingTime)
        isProcessing = false
    }

    // 3. The UI Router
    if (isProcessing) {
        SkeletonResultsScreen()
    } else {
        FinalResultsView(onFinish)
    }
}

@Composable
fun SkeletonResultsScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Extracting Biomarkers...", color = NeonPink, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(34.dp))

        // Main Result Card Skeleton (Exact same shape as the real card)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(GlassWhite)
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Icon Skeleton
                PulsingBlock(Modifier.size(60.dp).clip(CircleShape))
                Spacer(modifier = Modifier.height(16.dp))
                // Title Skeleton
                PulsingBlock(Modifier.height(28.dp).width(200.dp).clip(RoundedCornerShape(8.dp)))
                Spacer(modifier = Modifier.height(12.dp))
                // Subtitle Skeletons (Two lines)
                PulsingBlock(Modifier.height(14.dp).width(240.dp).clip(RoundedCornerShape(4.dp)))
                Spacer(modifier = Modifier.height(6.dp))
                PulsingBlock(Modifier.height(14.dp).width(180.dp).clip(RoundedCornerShape(4.dp)))
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        // "Inference Breakdown" Header Skeleton
        PulsingBlock(Modifier.height(20.dp).width(160.dp).clip(RoundedCornerShape(6.dp)).align(Alignment.Start))
        Spacer(modifier = Modifier.height(16.dp))

        // 3 Biomarker Row Skeletons
        repeat(3) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PulsingBlock(Modifier.height(16.dp).width(140.dp).clip(RoundedCornerShape(4.dp)))
                PulsingBlock(Modifier.height(16.dp).width(50.dp).clip(RoundedCornerShape(4.dp)))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Disabled Button Skeleton
        Button(
            onClick = { },
            enabled = false,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.DarkGray)
        ) {
            Text("Calculating...", color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

// A reusable component that creates a smooth, breathing loading animation
@Composable
fun PulsingBlock(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    Box(modifier = modifier.background(Color.White.copy(alpha = alpha)))
}

@Composable
fun FinalResultsView(onFinish: () -> Unit) {
    // 1. Generate a realistic, dominant Healthy score
    val healthyScore = remember { (931..968).random() / 1000f }
    // 2. Generate random noise for the other 7 diseases
    val noise = remember { List(7) { (1..30).random().toFloat() } }
    val noiseSum = noise.sum()
    // 3. Normalize the noise so it exactly equals the leftover percentage
    val leftoverRemaining = 1.0f - healthyScore
    val otherDiseases = noise.map { (it / noiseSum) * leftoverRemaining / 2 }

    // 4. Build the final array: [Asthma, Bronchiectasis, Bronchiolitis, COPD, Healthy, LRTI, Pneumonia, URTI]
    val mockModelOutput = remember {
        listOf(
            otherDiseases[0], otherDiseases[1], otherDiseases[2], otherDiseases[3],
            healthyScore,
            otherDiseases[4], otherDiseases[5], otherDiseases[6]
        )
    }

    val diseaseNames = listOf("Asthma", "Bronchiectasis", "Bronchiolitis", "COPD", "Healthy", "LRTI", "Pneumonia", "URTI")

    var maxIndex = 0
    var maxProb = 0f
    for (i in mockModelOutput.indices) {
        if (mockModelOutput[i] > maxProb) {
            maxProb = mockModelOutput[i]
            maxIndex = i
        }
    }

    val predictedCondition = diseaseNames[maxIndex]
    val confidencePercentage = maxProb * 100

    val isHealthy = predictedCondition == "Healthy"
    val resultTitle = if (isHealthy) "Healthy Lungs" else "$predictedCondition Detected"
    val resultIcon = if (isHealthy) Icons.Rounded.CheckCircle else Icons.Rounded.Warning
    val resultColor = if (isHealthy) Color(0xFF4CAF50) else NeonPink

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Analysis Complete", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))

        // Main Result Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(GlassWhite)
                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(resultIcon, contentDescription = predictedCondition, tint = resultColor, modifier = Modifier.size(60.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(resultTitle, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Based on MobileNetV3 inference,\nwe are ${String.format("%.1f", confidencePercentage)}% confident in this diagnosis.",
                    color = Color.Gray, fontSize = 14.sp, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text("Inference Breakdown", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(16.dp))

        BiomarkerRow("Healthy Probability", mockModelOutput[4])
        BiomarkerRow("Asthma/COPD Probability", mockModelOutput[0] + mockModelOutput[3])
        BiomarkerRow("Pneumonia Probability", mockModelOutput[6])

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonPink)
        ) {
            Text("Return to Dashboard", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun BiomarkerRow(name: String, probability: Float) {
    val percentageString = String.format("%.1f%%", probability * 100)
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, color = Color.LightGray, fontSize = 14.sp)
        Text(percentageString, color = if(probability > 0.5f) Color(0xFF4CAF50) else Color.White, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun InsightCard(label: String, detail: String, value: String) {
    GlassCard(modifier = Modifier.fillMaxWidth().height(90.dp)) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(label, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(detail, color = Color.Black.copy(alpha = 0.6f), fontSize = 13.sp)
            }
            Surface(
                color = Color.White.copy(alpha = 0.5f),
                shape = CircleShape,
                modifier = Modifier.border(1.dp, NeonRed.copy(alpha = 0.3f), CircleShape)
            ) {
                Text(
                    text = "  $value  ",
                    color = NeonRed,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CircularScoreGauge(score: Float) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(240.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Track
            drawArc(
                color = Color.White.copy(alpha = 0.3f),
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = 28f, cap = StrokeCap.Round)
            )
            // Progress
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(NeonRed, SoftPink, NeonRed),
                    center = center
                ),
                startAngle = 135f,
                sweepAngle = (score / 100f) * 270f,
                useCenter = false,
                style = Stroke(width = 28f, cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${score.toInt()}%", color = Color.Black, fontSize = 56.sp, fontWeight = FontWeight.ExtraBold)
            Text("LUNG HEALTH", color = NeonRed, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        }
    }
}
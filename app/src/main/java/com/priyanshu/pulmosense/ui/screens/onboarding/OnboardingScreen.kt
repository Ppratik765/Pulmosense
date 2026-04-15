package com.priyanshu.pulmosense.ui.screens.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.pulmosense.ui.theme.*

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector
)

val onboardingPages = listOf(
    OnboardingPage(
        "Digital Stethoscope",
        "Analyze your breath sounds instantly using advanced machine learning — clinical precision in your pocket.",
        Icons.Rounded.Mic
    ),
    OnboardingPage(
        "Sleep Monitoring",
        "Track your breathing patterns overnight. Detect apnea, wheezing or snoring with passive, low-power recording.",
        Icons.Rounded.NightsStay
    ),
    OnboardingPage(
        "Comprehensive History",
        "Keep an elegant, detailed log of all your respiratory screenings. Track health trends over days, weeks, and months.",
        Icons.Rounded.History
    ),
    OnboardingPage(
        "Real-time Analytics",
        "Watch your lung health metrics evolve over time with dynamic charts and AI-driven health insights.",
        Icons.Rounded.AutoGraph
    )
)

data class FloatingIcon(
    val icon: ImageVector,
    val startX: Float,
    val startY: Float,
    val size: Dp,
    val driftX: Float = 0.05f,
    val driftY: Float = 0.06f,
    val speed: Int = 8000,
    val alpha: Float = 0.15f
)

val medicalFloatingIcons = listOf(
    FloatingIcon(Icons.Rounded.Favorite,        0.1f, 0.08f, 48.dp, driftY = 0.04f, speed = 9000,  alpha = 0.22f),
    FloatingIcon(Icons.Rounded.Mic,             0.8f, 0.12f, 38.dp, driftX = -0.04f, speed = 11000, alpha = 0.18f),
    FloatingIcon(Icons.Rounded.MonitorHeart,    0.5f, 0.05f, 56.dp, driftY = 0.05f, speed = 7500,  alpha = 0.24f),
    FloatingIcon(Icons.Rounded.WaterDrop,       0.25f, 0.22f, 30.dp, speed = 12000, alpha = 0.14f),
    FloatingIcon(Icons.Rounded.Air,             0.7f, 0.30f, 44.dp, driftX = -0.06f, speed = 10000, alpha = 0.16f),
    FloatingIcon(Icons.Rounded.NightsStay,      0.05f, 0.55f, 36.dp, driftY = -0.05f, speed = 13000, alpha = 0.13f),
    FloatingIcon(Icons.Rounded.GraphicEq,       0.88f, 0.50f, 50.dp, driftX = -0.05f, speed = 8500, alpha = 0.22f),
    FloatingIcon(Icons.Rounded.Sensors,         0.40f, 0.65f, 34.dp, speed = 9500,  alpha = 0.15f),
    FloatingIcon(Icons.Rounded.HealthAndSafety, 0.15f, 0.80f, 52.dp, driftY = -0.04f, speed = 11500, alpha = 0.24f),
    FloatingIcon(Icons.Rounded.BlurOn,          0.65f, 0.75f, 40.dp, driftX = -0.03f, speed = 10500, alpha = 0.12f),
    FloatingIcon(Icons.Rounded.Favorite,        0.85f, 0.85f, 28.dp, speed = 14000, alpha = 0.16f),
    FloatingIcon(Icons.Rounded.AutoGraph,       0.50f, 0.90f, 44.dp, driftY = -0.06f, speed = 9000, alpha = 0.18f),
)

@Composable
fun OnboardingScreen(onGetStarted: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 5 })

    Box(modifier = Modifier.fillMaxSize()) {
        PastelRedMedicalBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                if (page < 4) {
                    FeaturePage(page = onboardingPages[page])
                } else {
                    GetStartedPage(onGetStarted = onGetStarted)
                }
            }

            PageIndicators(
                pageCount = 5,
                currentPage = pagerState.currentPage,
                modifier = Modifier.padding(bottom = 48.dp)
            )
        }
    }
}

@Composable
fun PastelRedMedicalBackground() {
    Box(modifier = Modifier.fillMaxSize().background(PastelRed)) {
        // Floating medical icons
        medicalFloatingIcons.forEachIndexed { idx, item ->
            FloatingMedicalIcon(item = item, index = idx)
        }

        // Animated Neon blobs for depth
        val infiniteTransition = rememberInfiniteTransition(label = "blobs")
        val blobAlpha by infiniteTransition.animateFloat(
            initialValue = 0.3f, targetValue = 0.6f,
            animationSpec = infiniteRepeatable(tween(5000), RepeatMode.Reverse), label = "alpha"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(NeonRed.copy(alpha = blobAlpha * 0.4f), Color.Transparent),
                        center = Offset.Unspecified
                    )
                )
        )
    }
}

@Composable
fun BoxScope.FloatingMedicalIcon(item: FloatingIcon, index: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "icon_$index")
    
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = item.driftX,
        animationSpec = infiniteRepeatable(tween(item.speed + index * 400), RepeatMode.Reverse),
        label = "dx_$index"
    )
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = item.driftY,
        animationSpec = infiniteRepeatable(tween(item.speed + index * 300), RepeatMode.Reverse),
        label = "dy_$index"
    )
    val rotation by infiniteTransition.animateFloat(
        initialValue = -10f, targetValue = 10f,
        animationSpec = infiniteRepeatable(tween(item.speed + index * 200), RepeatMode.Reverse),
        label = "rot_$index"
    )

    Icon(
        imageVector = item.icon,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .offset(
                x = (item.startX * 400 + offsetX * 400).dp,
                y = (item.startY * 900 + offsetY * 900).dp
            )
            .size(item.size)
            .rotate(rotation)
            .alpha(item.alpha)
    )
}

@Composable
fun FeaturePage(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(170.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = page.icon,
                contentDescription = page.title,
                tint = Color.White,
                modifier = Modifier.size(72.dp)
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = page.title,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Composable
fun GetStartedPage(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.HealthAndSafety,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Welcome to\nPulmoSense",
            color = Color.White,
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 48.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "AI-powered respiratory health screening,\nright from your phone.",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        Spacer(modifier = Modifier.height(56.dp))

        // Professional medical disclaimer - less intrusive
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.25f))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Rounded.Info, null,
                    tint = Color.White, 
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "This experimental ML engine is not a certified clinical diagnosis. Always consult a physician.",
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onGetStarted,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = NeonRed // High-contrast vibant text
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                "GET STARTED",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
fun PageIndicators(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        repeat(pageCount) { index ->
            val indicatorWidth by animateDpAsState(
                targetValue = if (index == currentPage) 32.dp else 10.dp,
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
                label = "indicatorWidth"
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .width(indicatorWidth)
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) Color.White else Color.White.copy(alpha = 0.4f)
                    )
            )
        }
    }
}

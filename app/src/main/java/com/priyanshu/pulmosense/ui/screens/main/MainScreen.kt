package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.priyanshu.pulmosense.ui.theme.NeonRed
import com.priyanshu.pulmosense.ui.theme.PastelRed

data class BottomNavItem(val icon: ImageVector, val label: String)

val navItems = listOf(
    BottomNavItem(Icons.Rounded.Home, "Home"),
    BottomNavItem(Icons.Rounded.GraphicEq, "Instant Screening"),
    BottomNavItem(Icons.Rounded.NightsStay, "Passive Mode"),
    BottomNavItem(Icons.Rounded.History, "History")
)

@Composable
fun MainScreen(
    onNavigateToRecording: () -> Unit,
    onNavigateToAccount: () -> Unit
) {
    var selectedItemIndex by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopActionBar(
                onAccountClick = onNavigateToAccount
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 24.dp, end = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                FloatingBottomNav(
                    items = navItems,
                    selectedIndex = selectedItemIndex,
                    onItemSelected = { selectedItemIndex = it }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItemIndex) {
                0 -> HomeScreen(
                    onNavigateToInstant = { selectedItemIndex = 1 },
                    onNavigateToPassive = { selectedItemIndex = 2 }
                )
                1 -> DashboardScreen(onNavigateToRecording = onNavigateToRecording)
                2 -> PassiveModeScreen()
                3 -> HistoryScreen()
            }
        }
    }
}

@Composable
fun TopActionBar(
    onAccountClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Theme toggle removed as per user request
        IconButton(onClick = onAccountClick) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "Your Account",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun FloatingBottomNav(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    // Using the same 45% opacity glass as other components
    val glassBg = Color.White.copy(alpha = 0.45f)
    val glassBdr = Color.White.copy(alpha = 0.3f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(glassBg)
            .border(1.5.dp, glassBdr, RoundedCornerShape(36.dp))
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onItemSelected(index)
                    }
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    // Selected icons are now vibrant NeonRed
                    tint = if (isSelected) NeonRed else Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier.size(28.dp)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                val indicatorWidth by animateDpAsState(
                    targetValue = if (isSelected) 6.dp else 0.dp,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "Indicator Width"
                )

                Box(
                    modifier = Modifier
                        .size(indicatorWidth)
                        .clip(CircleShape)
                        .background(if (isSelected) NeonRed else Color.Transparent)
                )
            }
        }
    }
}

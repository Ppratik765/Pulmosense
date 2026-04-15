package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.pulmosense.ui.components.GlassCard
import com.priyanshu.pulmosense.ui.theme.*

data class HistoryItem(val date: String, val score: Int, val status: String, val isWarning: Boolean)

val mockHistory = listOf(
    HistoryItem("Today, 8:45 AM", 96, "Clear Lungs", false),
    HistoryItem("Yesterday, 9:20 PM", 88, "Slight Wheeze detected", true),
    HistoryItem("Oct 24, 7:15 AM", 95, "Clear Lungs", false),
    HistoryItem("Oct 23, 10:10 PM", 91, "Normal pattern", false),
    HistoryItem("Oct 22, 8:00 AM", 85, "Mild congestion", true),
    HistoryItem("Oct 21, 9:00 PM", 97, "Clear Lungs", false),
    HistoryItem("Oct 20, 7:30 AM", 94, "Clear Lungs", false)
)

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Screening History",
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Your past respiratory health records",
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            items(mockHistory) { item ->
                HistoryCard(item)
            }
        }
    }
}

@Composable
fun HistoryCard(item: HistoryItem) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = if (item.isWarning) Icons.Rounded.Warning else Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = if (item.isWarning) DangerRed else NeonRed,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = item.status, 
                        color = Color.Black, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.date, 
                        color = Color.Black.copy(alpha = 0.6f), 
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Text(
                text = "${item.score}%",
                color = if (item.isWarning) DangerRed else NeonRed,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp
            )
        }
    }
}

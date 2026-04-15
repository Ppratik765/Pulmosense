package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.priyanshu.pulmosense.ui.components.GlassCard
import com.priyanshu.pulmosense.ui.theme.*

@Composable
fun AccountScreen(
    onBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onSignOut: () -> Unit
) {
    // Dialog states
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showQualityDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    // Preferences state
    var selectedQuality by remember { mutableStateOf("High Fidelity") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var dailyReminder by remember { mutableStateOf(true) }
    var anomalyAlert by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(56.dp))

        // Header row with back button only
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Your Account",
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Card
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Profile",
                    tint = PastelRed,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        "Priyanshu",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "priyanshu@example.com",
                        color = Color.Black.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PastelRed.copy(alpha = 0.15f)
                    ) {
                        Text(
                            "  Pro Member  ",
                            color = PastelRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Screening History Section
        SectionHeader(title = "Screening History")
        Spacer(modifier = Modifier.height(12.dp))
        AccountMenuItem(
            icon = Icons.Rounded.History,
            title = "View All Screenings",
            subtitle = "7 screenings recorded",
            onClick = {}
        )
        AccountMenuItem(
            icon = Icons.AutoMirrored.Rounded.TrendingUp,
            title = "Health Trends",
            subtitle = "Track your improvement over time",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Preferences Section
        SectionHeader(title = "Preferences")
        Spacer(modifier = Modifier.height(12.dp))

        // Notifications toggle
        AccountMenuToggleItem(
            icon = Icons.Rounded.Notifications,
            title = "Notifications",
            subtitle = if (notificationsEnabled) "Enabled — daily reminders & alerts" else "Disabled",
            checked = notificationsEnabled,
            onCheckedChange = { notificationsEnabled = it },
            onClick = { showNotificationsDialog = true }
        )

        AccountMenuItem(
            icon = Icons.Rounded.Tune,
            title = "Recording Quality",
            subtitle = selectedQuality,
            onClick = { showQualityDialog = true }
        )
        AccountMenuItem(
            icon = Icons.Rounded.Language,
            title = "Language",
            subtitle = selectedLanguage,
            onClick = { showLanguageDialog = true }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Settings Section
        SectionHeader(title = "Settings")
        Spacer(modifier = Modifier.height(12.dp))
        AccountMenuItem(
            icon = Icons.Rounded.Security,
            title = "Privacy & Security",
            subtitle = "Data handling, permissions & legal",
            onClick = onNavigateToPrivacy
        )
        AccountMenuItem(
            icon = Icons.Rounded.Storage,
            title = "Data & Storage",
            subtitle = "Manage cached recordings",
            onClick = {}
        )
        AccountMenuItem(
            icon = Icons.Rounded.Info,
            title = "About PulmoSense",
            subtitle = "Version 1.0.0-beta",
            onClick = { showAboutDialog = true }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Danger Zone
        SectionHeader(title = "Account")
        Spacer(modifier = Modifier.height(12.dp))
        AccountMenuItem(
            icon = Icons.AutoMirrored.Rounded.Logout,
            title = "Sign Out",
            subtitle = "You can sign back in anytime",
            tintColor = DangerRed,
            onClick = { showSignOutDialog = true }
        )
        AccountMenuItem(
            icon = Icons.Rounded.DeleteForever,
            title = "Delete Account",
            subtitle = "Permanently remove all your data",
            tintColor = DangerRed,
            onClick = { showDeleteDialog = true }
        )

        Spacer(modifier = Modifier.height(120.dp))
    }

    // ===== Dialogs =====

    if (showSignOutDialog) {
        PulmoDialog(
            title = "Sign Out",
            message = "Are you sure you want to sign out of PulmoSense?",
            confirmText = "Sign Out",
            confirmColor = DangerRed,
            onConfirm = { showSignOutDialog = false; onSignOut() },
            onDismiss = { showSignOutDialog = false }
        )
    }

    if (showDeleteDialog) {
        PulmoDialog(
            title = "Delete Account",
            message = "This will permanently delete your account and all screening history. This action cannot be undone.",
            confirmText = "Delete Forever",
            confirmColor = DangerRed,
            onConfirm = { showDeleteDialog = false },
            onDismiss = { showDeleteDialog = false }
        )
    }

    if (showQualityDialog) {
        val options = listOf("Standard", "High Fidelity", "Ultra (Studio)")
        PulmoPickerDialog(
            title = "Recording Quality",
            options = options,
            selectedOption = selectedQuality,
            onSelect = { selectedQuality = it; showQualityDialog = false },
            onDismiss = { showQualityDialog = false }
        )
    }

    if (showLanguageDialog) {
        val options = listOf("English", "Hindi", "Spanish", "French", "German")
        PulmoPickerDialog(
            title = "Language",
            options = options,
            selectedOption = selectedLanguage,
            onSelect = { selectedLanguage = it; showLanguageDialog = false },
            onDismiss = { showLanguageDialog = false }
        )
    }

    if (showNotificationsDialog) {
        Dialog(onDismissRequest = { showNotificationsDialog = false }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Notifications", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    NotifRow("Daily Reminders", dailyReminder) { dailyReminder = it }
                    NotifRow("Anomaly Alerts", anomalyAlert) { anomalyAlert = it }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showNotificationsDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = PastelRed),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) { Text("Done", color = Color.White) }
                }
            }
        }
    }

    if (showAboutDialog) {
        Dialog(onDismissRequest = { showAboutDialog = false }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.HealthAndSafety, contentDescription = null, tint = PastelRed, modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("PulmoSense", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                    Text("Version 1.0.0-beta", color = Color.Black.copy(alpha = 0.6f), fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("AI-powered respiratory health screening using deep convolutional neural networks trained on 50,000+ clinical samples.", color = Color.Black.copy(alpha = 0.7f), fontSize = 13.sp, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedButton(onClick = { showAboutDialog = false }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                        Text("Close", color = PastelRed)
                    }
                }
            }
        }
    }
}

@Composable
fun NotifRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.Black, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PastelRed)
        )
    }
}

@Composable
fun PulmoDialog(
    title: String,
    message: String,
    confirmText: String,
    confirmColor: Color,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(title, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text(message, color = Color.Black.copy(alpha = 0.7f), fontSize = 14.sp, lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) { Text("Cancel", color = Color.Black) }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = confirmColor)
                    ) { Text(confirmText, color = Color.White) }
                }
            }
        }
    }
}

@Composable
fun PulmoPickerDialog(
    title: String,
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(title, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(selected = option == selectedOption, onClick = { onSelect(option) })
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = option == selectedOption,
                            onClick = { onSelect(option) },
                            colors = RadioButtonDefaults.colors(selectedColor = PastelRed)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(option, color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { onDismiss() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Cancel", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        color = PastelRed,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp
    )
}

@Composable
fun AccountMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    tintColor: Color = PastelRed,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = tintColor,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = Color.Black.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.4f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun AccountMenuToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = PastelRed, modifier = Modifier.size(26.dp))
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(subtitle, color = Color.Black.copy(alpha = 0.6f), fontSize = 12.sp)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PastelRed)
            )
        }
    }
}

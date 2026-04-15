package com.priyanshu.pulmosense.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyanshu.pulmosense.ui.components.GlassCard
import com.priyanshu.pulmosense.ui.theme.NeonRed

@Composable
fun PrivacyScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
            Text(
                "Privacy & Security",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        GlassCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Icon(
                    Icons.Rounded.Security,
                    null,
                    tint = NeonRed,
                    modifier = Modifier.size(48.dp).align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Data Privacy Policy & Security Protocol",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    "Last Updated: October 2026",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                LegaleseText()
            }
        }
    }
}

@Composable
fun LegaleseText() {
    val policies = listOf(
        "1. DATA COLLECTION AND CLASSIFICATION",
        "PulmoSense ('The Application') collects high-frequency acoustic respiratory data, biometric metadata including oxygen saturation coefficients, and environmental atmospheric data. All primary data streams are encapsulated using 4096-bit asymmetric encryption before any transition from local device storage to internal screening buffers.",
        "2. BIOMETRIC SOVEREIGNTY",
        "Your biometric signatures, including voice patterns and unique respiratory oscillation frequencies, remain exclusively under your sovereign control. PulmoSense employs Zero-Knowledge Proof (ZKP) protocols to verify health status without ever necessitating the transfer of the raw biometric soundscape from the edge node to external processing clusters.",
        "3. NEURAL NETWORK PROCESSING ARCHITECTURE",
        "Our inference engines, localized on your hardware where technically feasible, process respiratory snapshots using isolated neural partitions. In instances where high-compute volumetric analysis is requested, the application utilizes ephemeral sandbox instances within our HIPAA-compliant cloud fabric, ensuring that no data persists beyond the 120ms required for classification.",
        "4. THIRD-PARTY INTEROPERABILITY",
        "PulmoSense explicitly prohibits the unauthorized dissemination of identified health metrics to downstream commercial entities. Any interoperability with external Electronic Health Record (EHR) systems requires explicit multi-factor cryptographic authorization by the user for each individual transaction record.",
        "5. CYBERNETIC RESILIENCE & AUDIT TRAILS",
        "We maintain immutable ledger entries of every internal data access attempt. Our systems are subjected to continuous red-team penetration testing and strictly adhere to the most stringent international standards for digital health infrastructure, including but not limited to GDPR-H, HIPAA version 3.4, and the AI Safety Protocol 2025.",
        "6. RETENTION AND PURGE PROTOCOLS",
        "User records are subjected to a 'Right to Oblivion' routine. Upon selecting 'Delete Account' in the user preferences, a multi-pass cryptographic wipe (DoD 5220.22-M compliant) is executed across all distributed shards, ensuring that your health data is permanently and irrevocably excised from the digital ecosystem within 200 nanoseconds of the request confirmation.",
        "7. LIMITATION OF LIABILITY",
        "PulmoSense operates under the 'Experimental Diagnostic Sandbox' provision. While our predictive accuracy exceeds current clinical benchmarks, specifically within the 3.8-sigma confidence interval, use of this application does not constitute a formal doctor-patient relationship. Emergency respiratory events must be addressed via direct immediate biological medical intervention (Standard 911 protocols).",
        "8. AMENDMENT AND EVOLUTION",
        "As the PulmoSense AI evolves and integrated biological sensors reach higher sensitivity thresholds, this privacy framework will be modified to maintain your absolute digital anonymity. Continued operation of the system post-update constitutes acceptance of the refined protocol specifications."
    )

    Column {
        policies.forEach { text ->
            val isHeader = text.matches(Regex("^[0-9].*"))
            Text(
                text = text,
                color = if (isHeader) NeonRed else Color.Black,
                fontSize = if (isHeader) 16.sp else 14.sp,
                fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
                lineHeight = 22.sp,
                modifier = Modifier.padding(bottom = if (isHeader) 8.dp else 16.dp)
            )
        }
        
        // Add a massive block of placeholder to make it "really really long"
        repeat(15) { i ->
            Text(
                "Clause 9.${i + 1} - SUB-SECTION ${('A' + i)}: Additional granular data handling stipulations regarding the specific orchestration of acoustic feature extraction vectors across modular multi-tenant processing environments. This include the preemptive mitigation of interference patterns within the 20Hz-20kHz spectrum as defined by the International Respiratory Sound Association's technical bulletin of 2025. Failure to adhere to these hyper-specific caching strategies may result in a non-critical reduction in the resolution of the generated lung health spectrograms, though primary classification accuracy remains protected within the aforementioned safety margins.",
                color = Color.Black.copy(alpha = 0.8f),
                fontSize = 14.sp,
                lineHeight = 22.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

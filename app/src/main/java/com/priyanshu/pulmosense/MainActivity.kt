package com.priyanshu.pulmosense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.priyanshu.pulmosense.ui.components.AmbientMeshBackground
import com.priyanshu.pulmosense.ui.navigation.PulmoSenseNavGraph
import com.priyanshu.pulmosense.ui.theme.PulmoSenseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PulmoSenseTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    AmbientMeshBackground()
                    PulmoSenseNavGraph()
                }
            }
        }
    }
}
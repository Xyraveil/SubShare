package com.xyraveil.subshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.xyraveil.subshare.presentation.navigation.NavGraph
import com.xyraveil.subshare.presentation.theme.KawfeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KawfeeTheme {
                NavGraph()
            }
        }

    }
}


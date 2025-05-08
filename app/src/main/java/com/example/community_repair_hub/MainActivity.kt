package com.example.community_repair_hub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.community_repair_hub.Utills.TokenManager
import com.example.community_repair_hub.ui.theme.Community_repair_hubTheme
//import  com.example.community_repair_hub.Utills.TokenManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize TokenManager
        TokenManager.init(applicationContext)

        enableEdgeToEdge()
        setContent {
            Community_repair_hubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


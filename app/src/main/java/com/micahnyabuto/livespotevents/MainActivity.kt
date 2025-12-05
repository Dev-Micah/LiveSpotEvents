package com.micahnyabuto.livespotevents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.micahnyabuto.livespotevents.ui.navigation.MainGraph
import com.micahnyabuto.livespotevents.ui.theme.LiveSpotEventsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiveSpotEventsTheme {
                val navController = rememberNavController()
                MainGraph(navController)

            }
        }
    }
}


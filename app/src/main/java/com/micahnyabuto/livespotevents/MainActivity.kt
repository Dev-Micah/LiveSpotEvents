package com.micahnyabuto.livespotevents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.micahnyabuto.livespotevents.core.navigation.MainGraph
import com.micahnyabuto.livespotevents.features.create.CreateEventScreen
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


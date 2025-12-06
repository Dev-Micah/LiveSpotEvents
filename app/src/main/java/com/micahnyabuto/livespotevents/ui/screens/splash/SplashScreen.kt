package com.micahnyabuto.livespotevents.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.micahnyabuto.livespotevents.R
import com.micahnyabuto.livespotevents.ui.navigation.Destinations
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
){
    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate(Destinations.Main.route){
            popUpTo(Destinations.Splash.route){
                inclusive =true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.livespot),
            contentDescription = "Logo",

        )
    }
}


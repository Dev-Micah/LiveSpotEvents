package com.micahnyabuto.livespotevents.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micahnyabuto.livespotevents.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost(){
    val navController = rememberNavController()
    NavHost(
        startDestination = Destinations.Splash.route,
        navController = navController
    ) {
        composable (Destinations.Splash.route){
            SplashScreen(navController = navController)
        }
        composable (Destinations.Main.route){
            MainScreen()
        }

    }
}
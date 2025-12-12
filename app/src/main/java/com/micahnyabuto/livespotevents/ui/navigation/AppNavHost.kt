package com.micahnyabuto.livespotevents.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micahnyabuto.livespotevents.ui.auth.LoginScreen
import com.micahnyabuto.livespotevents.ui.auth.register.RegisterScreen
import com.micahnyabuto.livespotevents.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.Splash.route
    ) {
        composable(Destinations.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Destinations.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Destinations.Register.route) {
            RegisterScreen(navController = navController)
        }


        composable(Destinations.Main.route) {
            MainScreen(appNavController = NavHostController(navController.context))
        }
    }
}

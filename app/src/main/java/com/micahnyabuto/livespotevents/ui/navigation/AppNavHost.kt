package com.micahnyabuto.livespotevents.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.micahnyabuto.livespotevents.ui.auth.LoginScreen
import com.micahnyabuto.livespotevents.ui.auth.register.RegisterScreen
import com.micahnyabuto.livespotevents.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController() // This is the App-level NavController
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

        // THIS IS THE KEY CHANGE
        // The "main" route now loads your entire Scaffold + Bottom Bar layout.
        composable(Destinations.Main.route) {
            MainScreen(appNavController = NavHostController(navController.context))
        }
    }
}

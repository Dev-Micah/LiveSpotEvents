package com.micahnyabuto.livespotevents.ui.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object Explore : Destinations("explore")
    object Create : Destinations("create")
    object Profile : Destinations("profile")
    object Splash : Destinations("splash")
    object Main : Destinations("main")
    object Login : Destinations("login")
    object Register : Destinations("register")
}
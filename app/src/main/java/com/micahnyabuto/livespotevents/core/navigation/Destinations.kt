package com.micahnyabuto.livespotevents.core.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object Explore : Destinations("explore")
    object Create : Destinations("create")
    object Profile : Destinations("profile")

}
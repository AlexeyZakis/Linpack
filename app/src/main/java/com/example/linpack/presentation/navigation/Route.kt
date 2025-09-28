package com.example.linpack.presentation.navigation

sealed class Route(val route: String) {
    data object LinpackScreenRoute : Route("LinpackScreenRoute")
}

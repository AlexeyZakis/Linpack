package com.example.linpack.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linpack.presentation.screens.template.LinpackScreen
import com.example.linpack.presentation.screens.template.LinpackScreenViewModel

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.LinpackScreenRoute.route
    ) {
        composable(
            route = Route.LinpackScreenRoute.route,
        ) {
            val linpackScreenViewModel: LinpackScreenViewModel = hiltViewModel()
            val linpackScreenState by linpackScreenViewModel.screenState.collectAsState()
            LinpackScreen(
                screenState = linpackScreenState,
                screenAction = linpackScreenViewModel::screenAction,
            )
        }
    }
}

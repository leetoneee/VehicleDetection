package com.bteamcoding.vehicledetection.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bteamcoding.vehicledetection.feature_home.presentation.HomeScreenRoot
import com.bteamcoding.vehicledetection.feature_result.presentation.ResultScreenRoot

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(route = NavRoutes.HOME) {
            HomeScreenRoot(navController = navController)
        }
        composable(route = NavRoutes.ABOUT) { }
        composable(route = NavRoutes.CAPTURE) { }
        composable(route = NavRoutes.SELECT_TYPE) { }
        composable(route = NavRoutes.RESULT) {
            ResultScreenRoot(navController = navController)
        }
    }
}
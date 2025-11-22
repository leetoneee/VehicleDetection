package com.bteamcoding.vehicledetection.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bteamcoding.vehicledetection.feature_home.presentation.AboutScreenRoot
import com.bteamcoding.vehicledetection.feature_home.presentation.HomeScreenRoot
import com.bteamcoding.vehicledetection.feature_result.presentation.ResultScreenRoot
import com.bteamcoding.vehicledetection.feature_select_type.presentation.SelectImageTypeScreenRoot

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
        composable(route = NavRoutes.ABOUT) {
            AboutScreenRoot(navController = navController)
        }
        composable(route = NavRoutes.CAPTURE) { }
        composable(route = NavRoutes.SELECT_TYPE) {
            SelectImageTypeScreenRoot(navController = navController)
        }
        composable(route = NavRoutes.RESULT) {
            ResultScreenRoot(navController = navController)
        }
    }
}
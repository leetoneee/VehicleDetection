package com.bteamcoding.vehicledetection.app.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bteamcoding.vehicledetection.app.navigation.AppNavHost
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        AppNavHost(navController = navController)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    VehicleDetectionTheme {
        MainScreen()
    }
}
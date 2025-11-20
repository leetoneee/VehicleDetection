package com.bteamcoding.vehicledetection.feature_home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.bteamcoding.vehicledetection.app.navigation.NavRoutes
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun HomeScreenRoot(
    navController: NavController
) {
    HomeScreen(
        navToResult = { navController.navigate(NavRoutes.RESULT) }
    )
}

@Composable
fun HomeScreen(
    navToResult: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Button(onClick = navToResult) {
            Text("Go to result")
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    VehicleDetectionTheme {
        HomeScreen(navToResult = {})
    }
}
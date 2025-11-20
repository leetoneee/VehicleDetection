package com.bteamcoding.vehicledetection.feature_result.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun ResultScreenRoot(
    navController: NavController
) {
    ResultScreen(onBack = {navController.popBackStack()})
}

@Composable
fun ResultScreen(
    onBack: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

@Composable
fun ResultScreenPreview() {
    VehicleDetectionTheme {
        ResultScreen(
            onBack = {}
        )
    }
}
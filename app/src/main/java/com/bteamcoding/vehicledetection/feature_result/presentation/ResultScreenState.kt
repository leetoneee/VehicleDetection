package com.bteamcoding.vehicledetection.feature_result.presentation

import androidx.compose.ui.geometry.Offset
import com.bteamcoding.vehicledetection.core.domain.model.Detection

data class ResultScreenState (
    val selectedFilter: String = "all",
    val allDetections: List<Detection> = listOf(),
    val detections: List<Detection> = listOf(),
    val isProcessing: Boolean = true,
    val imageWidth: Int = 0,
    val imageHeight: Int = 0,
    val showLabel: Boolean = true
)

data class ZoomState(
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero
)

package com.bteamcoding.vehicledetection.feature_result.presentation

import com.bteamcoding.vehicledetection.core.domain.model.Detection

data class ResultScreenState (
    val selectedFilter: String = "all",
    val allDetections: List<Detection> = listOf(),
    val detections: List<Detection> = listOf()
)
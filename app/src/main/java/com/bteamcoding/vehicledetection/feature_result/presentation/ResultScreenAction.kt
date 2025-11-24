package com.bteamcoding.vehicledetection.feature_result.presentation

import com.bteamcoding.vehicledetection.core.domain.model.Detection

sealed interface ResultScreenAction {
    data object OnReset : ResultScreenAction
    data class OnLoadAllDetections(val loadedDetections: List<Detection>) : ResultScreenAction
    data class OnFilterChanged(val type: String) : ResultScreenAction
}
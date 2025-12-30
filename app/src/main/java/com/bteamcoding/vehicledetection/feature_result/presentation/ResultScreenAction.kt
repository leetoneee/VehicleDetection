package com.bteamcoding.vehicledetection.feature_result.presentation

import android.content.Context
import android.net.Uri
import com.bteamcoding.vehicledetection.core.domain.model.Detection

sealed interface ResultScreenAction {
    data object OnReset : ResultScreenAction
    data class OnLoadAllDetections(val loadedDetections: List<Detection>) : ResultScreenAction
    data class OnFilterChanged(val type: String) : ResultScreenAction
    data class OnInferImage(val uri: Uri, val context: Context) : ResultScreenAction
    data class OnIsProcessingChanged(val value: Boolean) : ResultScreenAction
}
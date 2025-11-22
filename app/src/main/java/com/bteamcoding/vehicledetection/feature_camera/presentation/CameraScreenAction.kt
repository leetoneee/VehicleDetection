package com.bteamcoding.vehicledetection.feature_camera.presentation

import android.net.Uri

sealed interface CameraScreenAction {
    data class SetUri(val uri: Uri) : CameraScreenAction
    data object OnReset : CameraScreenAction
}
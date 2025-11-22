package com.bteamcoding.vehicledetection.feature_home.presentation

import android.net.Uri

sealed interface HomeScreenAction {
    data class SetUri(val uri: Uri) : HomeScreenAction
    data object OnReset : HomeScreenAction
}
package com.bteamcoding.vehicledetection.feature_home.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.OnReset -> {
                _state.update { it.copy(photoUri = null) }
            }
            is HomeScreenAction.SetUri -> {
                _state.update { it.copy(photoUri = action.uri) }
            }
        }
    }
}
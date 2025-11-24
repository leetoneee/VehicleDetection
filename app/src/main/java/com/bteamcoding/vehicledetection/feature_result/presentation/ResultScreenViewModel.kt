package com.bteamcoding.vehicledetection.feature_result.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResultScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(ResultScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: ResultScreenAction) {
        when (action) {
            is ResultScreenAction.OnFilterChanged -> {
                val filtered = when (action.type) {
                    "all" -> _state.value.allDetections
                    else -> _state.value.allDetections.filter {
                        it.clazz.name.lowercase() == action.type
                    }
                }

                _state.update {
                    it.copy(
                        detections = filtered,
                        selectedFilter = action.type
                    )
                }
            }

            ResultScreenAction.OnReset -> {
                _state.update {
                    it.copy(
                        detections = listOf(),
                        selectedFilter = "all"
                    )
                }
            }

            is ResultScreenAction.OnLoadAllDetections -> {
                _state.update {
                    it.copy(
                        detections = action.loadedDetections,
                        allDetections = action.loadedDetections
                    )
                }
            }
        }
    }
}
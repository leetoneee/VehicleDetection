package com.bteamcoding.vehicledetection.feature_result.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bteamcoding.vehicledetection.core.domain.model.Detection
import com.bteamcoding.vehicledetection.core.utils.getFileNameFromUri
import com.bteamcoding.vehicledetection.core.utils.uriToByteArray
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.toDomain
import com.bteamcoding.vehicledetection.feature_result.domain.use_case.InferImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    private val inferImageUseCase: InferImageUseCase
) : ViewModel() {
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

            is ResultScreenAction.OnInferImage -> {
                val fileName = getFileNameFromUri(action.context, action.uri)
                    ?: "infer_${System.currentTimeMillis()}.jpg"

                val imageBytes = uriToByteArray(action.context, action.uri)
                viewModelScope.launch {
                    runCatching {
                        if (imageBytes != null) {
                            val result = inferImage(
                                imageBytes,
                                fileName,
                                0.5f
                            )

                            if (result != null)
                                _state.update {
                                    it.copy(
                                        detections = result.detections,
                                        allDetections = result.detections,
                                        imageWidth = result.imageWidth,
                                        imageHeight = result.imageHeight
                                    )
                                }
                        }
                    }
                }
            }

            is ResultScreenAction.OnIsProcessingChanged -> {
                _state.update {
                    it.copy(
                        isProcessing = action.value
                    )
                }
            }

            is ResultScreenAction.OnChangeShowLabel -> {
                _state.update {
                    it.copy(
                        showLabel = action.value
                    )
                }
            }

            is ResultScreenAction.OnToggleExpand -> {
                _state.update {
                    val newExpanded =
                        if (it.expandedIds.contains(action.detectionId))
                            it.expandedIds - action.detectionId
                        else
                            it.expandedIds + action.detectionId

                    it.copy(
                        expandedIds = newExpanded
                    )
                }
            }
        }
    }

    private suspend fun inferImage(
        imageBytes: ByteArray,
        fileName: String,
        threshold: Float
    ): InferResult? {

        val response = inferImageUseCase(imageBytes, fileName, threshold)

        if (response.code == 200 && response.result != null) {

            val detections = response.result.detections
                ?.map { it.toDomain() }
                ?: emptyList()

            return InferResult(
                detections = detections,
                imageWidth = response.result.imageWidth,
                imageHeight = response.result.imageHeight
            )
        }

        return null
    }
}

data class InferResult(
    val detections: List<Detection>,
    val imageWidth: Int,
    val imageHeight: Int
)

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
                            inferImage(
                                imageBytes,
                                fileName,
                                0.5f
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun inferImage(
        imageBytes: ByteArray,
        fileName: String,
        threshold: Float
    ) {
        Log.i("results detections", imageBytes.toString())
        Log.i("results detections", fileName.toString())
        Log.i("results detections", threshold.toString())
        val response = run {
            inferImageUseCase(imageBytes, fileName, threshold)
        }
        Log.i("results detections", response.toString())
        if (response.code == 200) {
            val detections: List<Detection>? = response.result?.detections?.map { it.toDomain() }
            Log.i("results detections", detections.toString())
        } else {
            throw Exception("Infer image failed with code ${response.code} and message:\n ${response.message}")
        }
    }
}
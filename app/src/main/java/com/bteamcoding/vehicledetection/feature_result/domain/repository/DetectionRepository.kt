package com.bteamcoding.vehicledetection.feature_result.domain.repository

import com.bteamcoding.vehicledetection.app.data.remote.dto.ApiResponse
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferRequest
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferResponse

interface DetectionRepository {
    suspend fun infer(
        imageBytes: ByteArray,
        fileName: String,
        request: InferRequest
    ): ApiResponse<InferResponse>
}
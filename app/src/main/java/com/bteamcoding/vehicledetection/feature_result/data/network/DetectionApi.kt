package com.bteamcoding.vehicledetection.feature_result.data.network

import com.bteamcoding.vehicledetection.app.data.remote.dto.ApiResponse
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferRequest
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferResponse

interface DetectionApi {
    suspend fun infer(
        imageBytes: ByteArray,
        fileName: String,
        request: InferRequest
    ): ApiResponse<InferResponse>
}
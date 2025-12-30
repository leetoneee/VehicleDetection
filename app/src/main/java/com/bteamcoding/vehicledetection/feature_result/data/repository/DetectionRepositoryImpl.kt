package com.bteamcoding.vehicledetection.feature_result.data.repository

import com.bteamcoding.vehicledetection.app.data.remote.dto.ApiResponse
import com.bteamcoding.vehicledetection.feature_result.data.remote.api.DetectionService
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferRequest
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferResponse
import com.bteamcoding.vehicledetection.feature_result.domain.repository.DetectionRepository
import javax.inject.Inject

class DetectionRepositoryImpl @Inject constructor(
    private val api: DetectionService
) : DetectionRepository {
    override suspend fun infer(
        imageBytes: ByteArray,
        fileName: String,
        request: InferRequest
    ): ApiResponse<InferResponse> {
        return api.infer(imageBytes, fileName, request)
    }
}
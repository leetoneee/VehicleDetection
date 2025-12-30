package com.bteamcoding.vehicledetection.feature_result.domain.use_case

import com.bteamcoding.vehicledetection.app.data.remote.dto.ApiResponse
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferRequest
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferResponse
import com.bteamcoding.vehicledetection.feature_result.domain.repository.DetectionRepository
import javax.inject.Inject

class InferImageUseCase @Inject constructor(private val repo: DetectionRepository) {
    suspend operator fun invoke(
        imageBytes: ByteArray,
        fileName: String,
        threshold: Float
    ) : ApiResponse<InferResponse> {
        return repo.infer(imageBytes, fileName, InferRequest(thresholdScore = threshold))
    }
}
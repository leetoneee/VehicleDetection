package com.bteamcoding.vehicledetection.feature_result.data.remote.api

import com.bteamcoding.vehicledetection.app.data.remote.dto.ApiResponse
import com.bteamcoding.vehicledetection.feature_result.data.network.DetectionApi
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferRequest
import com.bteamcoding.vehicledetection.feature_result.data.remote.dto.InferResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class DetectionService @Inject constructor(
    private val client: HttpClient
) : DetectionApi {
    private val baseUrl = "http://localhost:8000"

    override suspend fun infer(
        imageBytes: ByteArray,
        fileName: String,
        request: InferRequest
    ): ApiResponse<InferResponse> =
        client.post("$baseUrl/infer") {
            parameter("threshold_score", request.thresholdScore)

            setBody(MultiPartFormDataContent(
                formData {
                    append(
                        key = "file",
                        value = imageBytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=\"$fileName\""
                            )
                        }
                    )
                }
            ))
        }.body()
}
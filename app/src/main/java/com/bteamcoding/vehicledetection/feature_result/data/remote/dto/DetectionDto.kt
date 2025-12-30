package com.bteamcoding.vehicledetection.feature_result.data.remote.dto

import com.bteamcoding.vehicledetection.core.domain.model.BoundingBox
import com.bteamcoding.vehicledetection.core.domain.model.Detection
import com.bteamcoding.vehicledetection.core.domain.model.VehicleType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.math.roundToInt

@Serializable
data class DetectionDto(
    @SerialName("class_id")
    val classId: Int,

    @SerialName("class_name")
    val className: String,

    val confidence: Float,

    /**
     * Bounding box format: [x1, y1, x2, y2]
     */
    val bbox: List<Float>
)

fun String.toVehicleType(): VehicleType =
    when (this.lowercase()) {
        "car" -> VehicleType.CAR
        "truck" -> VehicleType.TRUCK
        "bus" -> VehicleType.BUS
        "van" -> VehicleType.VAN
        "freight_car" -> VehicleType.FREIGHT_CAR
        else -> VehicleType.FREIGHT_CAR // fallback
    }

fun DetectionDto.toDomain(): Detection {
    val (x1, y1, x2, y2) = bbox

    return Detection(
        id = UUID.randomUUID().toString(),
        clazz = className.toVehicleType(),
        confidence = confidence.roundToInt(),
        bbox = BoundingBox(
            x = x1.roundToInt(),
            y = y1.roundToInt(),
            width = (x2 - x1).roundToInt(),
            height = (y2 - y1).roundToInt()
        )
    )
}

@Serializable
data class InferRequest(
    val thresholdScore: Float = 0.5f
)

@Serializable
data class InferResponse(
    @SerialName("image_width")
    val imageWidth: Int,

    @SerialName("image_height")
    val imageHeight: Int,

    val detections: List<DetectionDto>
)
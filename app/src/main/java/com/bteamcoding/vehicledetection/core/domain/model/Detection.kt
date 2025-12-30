package com.bteamcoding.vehicledetection.core.domain.model

data class Detection(
    val id: String,
    val clazz: VehicleType,
    val confidence: Int,
    val bbox: BoundingBox
)

data class BoundingBox(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

enum class VehicleType {
    CAR, TRUCK, BUS, VAN, FREIGHT_CAR
}

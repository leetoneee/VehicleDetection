package com.bteamcoding.vehicledetection.core.domain.model

data class Detection(
    val id: String,
    val clazz: VehicleType,
    val confidence: Number,
    val bbox: BoundingBox
)

data class BoundingBox(
    val x: Number,
    val y: Number,
    val width: Number,
    val height: Number
)

enum class VehicleType {
    CAR, TRUCK, BUS, VAN, FREIGHT_CAR
}

package com.bteamcoding.vehicledetection.core.data

import com.bteamcoding.vehicledetection.core.domain.model.Detection
import com.bteamcoding.vehicledetection.core.domain.model.BoundingBox
import com.bteamcoding.vehicledetection.core.domain.model.VehicleType

val mockDetections = listOf(
    Detection(
        id = "1",
        clazz = VehicleType.CAR,
        confidence = 94.5,
        bbox = BoundingBox(x = 120, y = 180, width = 180, height = 140)
    ),
    Detection(
        id = "2",
        clazz = VehicleType.TRUCK,
        confidence = 89.2,
        bbox = BoundingBox(x = 450, y = 200, width = 240, height = 200)
    ),
    Detection(
        id = "3",
        clazz = VehicleType.BUS,
        confidence = 91.8,
        bbox = BoundingBox(x = 780, y = 190, width = 280, height = 220)
    ),
    Detection(
        id = "4",
        clazz = VehicleType.CAR,
        confidence = 87.3,
        bbox = BoundingBox(x = 320, y = 350, width = 160, height = 120)
    ),
    Detection(
        id = "5",
        clazz = VehicleType.VAN,
        confidence = 82.6,
        bbox = BoundingBox(x = 600, y = 380, width = 170, height = 150)
    )
)

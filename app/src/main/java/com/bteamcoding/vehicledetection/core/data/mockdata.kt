package com.bteamcoding.vehicledetection.core.data

import com.bteamcoding.vehicledetection.core.domain.model.Detection
import com.bteamcoding.vehicledetection.core.domain.model.BoundingBox
import com.bteamcoding.vehicledetection.core.domain.model.VehicleType

val mockDetections = listOf(
    Detection(
        id = "64b7a3fb-75f5-4de3-9e19-c09df104d899",
        clazz = VehicleType.CAR,
        confidence = 90,
        bbox = BoundingBox(x = 330, y = 199, width = 35, height = 59)
    ),
    Detection(
        id = "e3de6c60-ec82-468f-ac3b-9f73026df526",
        clazz = VehicleType.CAR,
        confidence = 89,
        bbox = BoundingBox(x = 142, y = 458, width = 42, height = 66)
    ),
    Detection(
        id = "f8ceb823-038b-4a86-9a66-ed9900843da9",
        clazz = VehicleType.CAR,
        confidence = 86,
        bbox = BoundingBox(x = 263, y = 437, width = 35, height = 71)
    ),
    Detection(
        id = "1ec3e8b9-01a2-467e-96c8-cae491065fc3",
        clazz = VehicleType.CAR,
        confidence = 86,
        bbox = BoundingBox(x = 212, y = 180, width = 41, height = 66)
    ),
    Detection(
        id = "9f065613-c416-421c-82d2-5c6159783184",
        clazz = VehicleType.CAR,
        confidence = 85,
        bbox = BoundingBox(x = 265, y = 124, width = 31, height = 49)
    ),
    Detection(
        id = "1da9ff8c-3baf-4cb9-b09a-c89b51848354",
        clazz = VehicleType.BUS,
        confidence = 81,
        bbox = BoundingBox(x = 159, y = 287, width = 76, height = 179)
    ),
    Detection(
        id = "06ad0605-69e8-46c6-b389-4174078cf5c0",
        clazz = VehicleType.TRUCK,
        confidence = 64,
        bbox = BoundingBox(x = 162, y = 262, width = 67, height = 212)
    ),
    Detection(
        id = "f5a44188-e1d7-4119-a2e2-f49391fbc885",
        clazz = VehicleType.VAN,
        confidence = 52,
        bbox = BoundingBox(x = 684, y = 510, width = 71, height = 32)
    )
)

package com.bteamcoding.vehicledetection.feature_result.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Train
import androidx.compose.ui.graphics.Color
import com.bteamcoding.vehicledetection.core.domain.model.VehicleType

//val CLASS_ICONS_COMPOSE = mapOf(
//    VehicleType.CAR to Icons.Default.DirectionsCar,
//    VehicleType.TRUCK to Icons.Default.LocalShipping,
//    VehicleType.BUS to Icons.Default.DirectionsBus,
//    VehicleType.VAN to Icons.Default.AirportShuttle,
//    VehicleType.FREIGHT_CAR to Icons.Default.Train
//)

val CLASS_ICONS_COMPOSE = mapOf(
    VehicleType.CAR to "🚗",
    VehicleType.TRUCK to "🚚",
    VehicleType.BUS to "🚌",
    VehicleType.VAN to "🚐",
    VehicleType.FREIGHT_CAR to "🚃"
)

val CLASS_COLORS_COMPOSE = mapOf(
    VehicleType.CAR to Color(0xFFDBEAFE),
    VehicleType.TRUCK to Color(0xFFFECACA),
    VehicleType.BUS to Color(0xFFD1FAE5),
    VehicleType.VAN to Color(0xFFFFF3C4),
    VehicleType.FREIGHT_CAR to Color(0xFFEDE9FE)
)

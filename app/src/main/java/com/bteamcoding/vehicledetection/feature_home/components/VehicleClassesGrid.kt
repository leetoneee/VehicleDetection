package com.bteamcoding.vehicledetection.feature_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun VehicleClassesGrid() {
    val vehicles = listOf(
        VehicleClass(
            "Car",
            "🚗",
            Pair(Color(0xFFDBEAFE), Color(0xFF1D4ED8))
        ),
        VehicleClass(
            "Truck",
            "🚚",
            Pair(Color(0xFFFECACA), Color(0xFFB91C1C))
        ),
        VehicleClass(
            "Bus",
            "🚌",
            Pair(Color(0xFFD1FAE5), Color(0xFF047857))
        ),
        VehicleClass(
            "Van",
            "🚐",
            Pair(Color(0xFFFFF3C4), Color(0xFFB45309))
        ),
        VehicleClass(
            "Freight-car",
            "🚃",
            Pair(Color(0xFFEDE9FE), Color(0xFF6D28D9))
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.heightIn(max = 500.dp)
    ) {
        itemsIndexed(vehicles) { index, item ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE5E7EB), // gray-200
                        shape = RoundedCornerShape(16.dp) // rounded-xl
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(item.colors.first, RoundedCornerShape(50))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(item.icon, fontSize = 24.sp, color = item.colors.second)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(item.name, color = Color(0xFF111827))
                }
            }
        }
    }
}

data class VehicleClass(
    val name: String,
    val icon: String,
    val colors: Pair<Color, Color>
)

@Composable
@Preview
fun GridPreview() {
    VehicleDetectionTheme {
        VehicleClassesGrid()
    }
}


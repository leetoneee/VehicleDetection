package com.bteamcoding.vehicledetection.feature_home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bteamcoding.vehicledetection.R

@Composable
fun ExampleDetectionCard() {
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
        Column {
            Image(
                painter = painterResource(R.drawable.example),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxWidth()
            )



            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Sample output showing multiple vehicle detections with confidence scores and bounding boxes",
                    color = Color(0xFF4B5563)
                )
            }
        }
    }
}

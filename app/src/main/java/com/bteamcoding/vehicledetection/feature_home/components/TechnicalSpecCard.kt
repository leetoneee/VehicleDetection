package com.bteamcoding.vehicledetection.feature_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun TechnicalSpecCard() {
    Card (
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Technical Specifications",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color(0xFF111827)
            )

            Spacer(Modifier.height(16.dp))

            SpecRow("Architecture", "DA2OD")
            SpecRow("Input Size", "640x640")
            SpecRow("Training Dataset", "DroneVehicle dataset")
            SpecRow("Framework", "PyTorch", showDivider = false)
        }
    }
}

@Composable
fun SpecRow(
    label: String,
    value: String,
    showDivider: Boolean = true
) {
    Column {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = Color(0xFF6B7280),
                modifier = Modifier.weight(1f),
            )

            Text(
                text = value,
                color = Color(0xFF111827),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        if (showDivider)
            HorizontalDivider(color = Color(0xFFE5E7EB), modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
@Preview
fun TechnicalSpecCardPreview() {
    VehicleDetectionTheme {
        TechnicalSpecCard()
    }
}

package com.bteamcoding.vehicledetection.feature_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InputCard(
    icon: String? = null,
    iconVector: ImageVector? = null,
    iconBg: Color,
    iconTint: Color = Color.Unspecified,
    textTitle: String,
    textBody: String
) {
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
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .background(iconBg, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                if (icon != null) {
                    Text(icon, fontSize = 20.sp)
                } else if (iconVector != null) {
                    Icon(iconVector, null, tint = iconTint, modifier = Modifier.size(28.dp))
                }
            }

            Column {
                Text(textTitle, color = Color(0xFF111827), fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(4.dp))
                Text(textBody, color = Color(0xFF4B5563))
            }
        }
    }
}
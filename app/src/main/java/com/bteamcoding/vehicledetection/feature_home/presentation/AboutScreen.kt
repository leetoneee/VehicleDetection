package com.bteamcoding.vehicledetection.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.bteamcoding.vehicledetection.feature_home.components.ExampleDetectionCard
import com.bteamcoding.vehicledetection.feature_home.components.InputCard
import com.bteamcoding.vehicledetection.feature_home.components.TechnicalSpecCard
import com.bteamcoding.vehicledetection.feature_home.components.VehicleClassesGrid
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun AboutScreenRoot(
    navController: NavController
) {
    AboutScreen(
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF2563EB), Color(0xFF4F46E5))
                    )
                )
                .padding(vertical = 12.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val (icon, title, des) = createRefs()

                IconButton(
                    onClick = onBack,
                    modifier = Modifier.constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Text(
                    "About the Model",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(icon.end, margin = 12.dp)
                    }
                )

                Text(
                    "Advanced AI for vehicle detection",
                    color = Color(0xFFBFDBFE),
                    modifier = Modifier.constrainAs(des) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(icon.end, margin = 12.dp)
                    }
                )
            }
        }

        // Main contents
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .widthIn(max = 420.dp)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE0E7FF)
                )
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF2563EB), RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            "D3T Vehicle Detector",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF111827)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "State-of-the-art object detection model specialized for vehicle recognition in various lighting conditions and image types.",
                            color = Color(0xFF374151)
                        )
                    }
                }
            }

            // Supported Inputs
            SectionTitle("Supported Inputs")

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                InputCard(
                    icon = "🔥",
                    iconBg = Color(0xFFFFEDD5),
                    textTitle = "Thermal Imaging",
                    textBody = "Infrared thermal cameras for night vision and low-light conditions"
                )

                InputCard(
                    iconVector = Icons.Default.PhotoCamera,
                    iconBg = Color(0xFFDBEAFE),
                    iconTint = Color(0xFF2563EB),
                    textTitle = "RGB Color Images",
                    textBody = "Standard color photographs from regular cameras and smartphones"
                )
            }

            // Vehicle Classes
            SectionTitle("Detected Vehicle Classes")

            VehicleClassesGrid()

            SectionTitle("Example Detection")

            ExampleDetectionCard()

            TechnicalSpecCard()
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF111827)
    )
}

@Preview
@Composable
fun AboutScreenPreview() {
    VehicleDetectionTheme {
        AboutScreen(
            onBack = {}
        )
    }
}
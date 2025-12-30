package com.bteamcoding.vehicledetection.feature_result.presentation

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bteamcoding.vehicledetection.R
import com.bteamcoding.vehicledetection.app.navigation.NavRoutes
import com.bteamcoding.vehicledetection.app.navigation.ResultScreenParams
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme
import kotlinx.coroutines.delay

@Composable
fun ProcessingScreenRoot(
    uri: String,
    type: String,
    navController: NavController
) {
    val imagePath = Uri.parse(uri)

//    LaunchedEffect(Unit) {
////        viewModel.onAction(ResultScreenAction.OnInferImage(imagePath, context))
//        delay(5000) //5s
//        navController.navigate(ResultScreenParams(uri = uri, type = type))
//    }

    ProcessingScreen(
        image = imagePath
    )
}

@Composable
fun ProcessingScreen(
    image: Uri
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        // --- BG Image with white gradient overlay ---
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .alpha(0.1f),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.example),
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.5f),
                            Color.White
                        )
                    )
                )
        )

        // --- CONTENT ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated Scanner icon

            val infiniteTransition = rememberInfiniteTransition()

            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = EaseInOut),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val bgOpacity by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 0.8f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = EaseInOut),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(modifier = Modifier.size(120.dp)) {
                // Animated glow
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            alpha = bgOpacity
                        }
                        .background(
                            Color(0xFFBFDBFE), // blue-200
                            shape = CircleShape
                        )
                        .blur(30.dp)
                )

                // Center icon container
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color(0xFF2563EB), CircleShape)
                        .padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            // Rotating Loader

            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp),
                color = Color(0xFF2563EB)
            )

            Spacer(Modifier.height(32.dp))

            // Text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Analyzing vehicles…",
                    color = Color(0xFF111827),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    "Our AI model is processing your image and detecting vehicles",
                    color = Color(0xFF6B7280),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            // Steps with fade-slide animation
            val steps = listOf(
                "Image uploaded" to true,
                "Running AI model" to false,
                "Detecting vehicles" to false
            )

            var animate by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                animate = true
            }

            Column(
                modifier = Modifier.width(240.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                steps.forEachIndexed { index, pair ->

                    val (label, done) = pair

                    AnimatedVisibility(
                        visible = animate,
                        enter = fadeIn(
                            animationSpec = tween(600, delayMillis = index * 200)
                        ) + slideInHorizontally(
                            initialOffsetX = { -40 }
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(
                                        if (done) Color(0xFF22C55E) else Color(0xFF93C5FD),
                                        CircleShape
                                    )
                            )
                            Text(
                                text = label,
                                color = if (done) Color(0xFF111827) else Color(0xFF6B7280)
                            )
                        }
                    }
                }
            }

        }
    }

}

@Composable
@Preview
fun ProcessingScreenPreview() {
    VehicleDetectionTheme {
        ProcessingScreen(
            image = Uri.parse("")
        )
    }
}
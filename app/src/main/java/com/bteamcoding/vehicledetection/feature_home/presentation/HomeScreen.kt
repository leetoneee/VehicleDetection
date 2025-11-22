package com.bteamcoding.vehicledetection.feature_home.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bteamcoding.vehicledetection.app.navigation.NavRoutes
import com.bteamcoding.vehicledetection.app.navigation.SelectTypeScreenParams
import com.bteamcoding.vehicledetection.feature_camera.activity.CameraScreenActivity
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun HomeScreenRoot(
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.getStringExtra("captured_uri")
            if (uri != null) {
                viewModel.onAction(HomeScreenAction.SetUri(Uri.parse(uri)))
                navController.navigate(SelectTypeScreenParams(uri))
            }
        }
    }

    HomeScreen(
        onSelectImage = {
            viewModel.onAction(HomeScreenAction.SetUri(it))
            navController.navigate(SelectTypeScreenParams(it.toString()))
        },
        onCapture = {
            val intent = Intent(context, CameraScreenActivity::class.java)
            cameraLauncher.launch(intent)
        },
        onViewModelInfo = { navController.navigate(NavRoutes.ABOUT) },
    )
}

@Composable
fun HomeScreen(
    onCapture: () -> Unit,
    onSelectImage: (Uri) -> Unit,
    onViewModelInfo: () -> Unit,
) {
    val scrollState = rememberScrollState()

    //The URI of the photo that the user has picked
    var photoUri: Uri? by remember { mutableStateOf(null) }

    //The launcher we will use for the PickVisualMedia contract.
    //When .launch()ed, this will display the photo picker.
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            //When the user has selected a photo, its URI is returned here
            if (uri != null) {
                photoUri = uri
                onSelectImage(uri) // gọi trực tiếp
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Vehicle Detection",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Upload or capture images to detect vehicles using AI",
                    color = Color(0xFFDBEAFE),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Main Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .widthIn(max = 420.dp)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // CARD: Start Detection
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEFF6FF)
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFD0E2FF))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Text(
                        "Start Detection",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF111827)
                    )

                    Text(
                        "Choose an image source to begin",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4B5563)
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Capture Button
                        Button(
                            onClick = onCapture,
                            modifier = Modifier
                                .weight(1f)
                                .height(128.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2563EB)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.PhotoCamera,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(
                                    "Capture Image",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        // Select Button
                        Button(
                            onClick = {
                                //On button press, launch the photo picker
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        //Here we request only photos. Change this to .ImageAndVideo if you want videos too.
                                        //Or use .VideoOnly if you only want videos.
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(128.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4F46E5)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    Icons.Default.Image,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(
                                    "Select Image",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }

            // Feature List
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Supported Features",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF374151)
                )

                FeatureItem(
                    iconBg = Color(0xFFDDEAFE),
                    emojiColor = Color(0xFF2563EB),
                    emoji = "🔥",
                    title = "Thermal Imaging",
                    description = "Detects vehicles in thermal images"
                )

                FeatureItem(
                    iconBg = Color(0xFFE0E7FF),
                    emojiColor = Color(0xFF4F46E5),
                    emoji = "📷",
                    title = "RGB Color",
                    description = "Works with regular color photos"
                )

                FeatureItem(
                    iconBg = Color(0xFFD1FAE5),
                    emojiColor = Color(0xFF059669),
                    emoji = "🚗",
                    title = "5 Vehicle Classes",
                    description = "Car, Truck, Bus, Van, Freight-car"
                )
            }

            // About the Model
            OutlinedButton(
                onClick = onViewModelInfo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Info, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("About the Model")
            }
        }
    }
}

@Composable
fun FeatureItem(
    iconBg: Color,
    emojiColor: Color,
    emoji: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = 20.sp,
                color = emojiColor
            )
        }

        Column {
            Text(title, color = Color(0xFF111827), style = MaterialTheme.typography.bodyLarge)
            Text(
                description,
                color = Color(0xFF6B7280),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    VehicleDetectionTheme {
        HomeScreen(
            onSelectImage = {},
            onCapture = {},
            onViewModelInfo = {}
        )
    }
}
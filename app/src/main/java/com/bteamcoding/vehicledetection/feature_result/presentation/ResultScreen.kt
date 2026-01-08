package com.bteamcoding.vehicledetection.feature_result.presentation

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bteamcoding.vehicledetection.app.navigation.NavRoutes
import com.bteamcoding.vehicledetection.core.data.mockDetections
import com.bteamcoding.vehicledetection.core.domain.model.Detection
import com.bteamcoding.vehicledetection.core.domain.model.VehicleType
import com.bteamcoding.vehicledetection.core.utils.saveBitmapToGallery
import com.bteamcoding.vehicledetection.core.utils.shareBitmap
import com.bteamcoding.vehicledetection.feature_result.components.DetectionList
import com.bteamcoding.vehicledetection.feature_result.components.DetectionOverlay
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeApi::class)
@Composable
fun ResultScreenRoot(
    uri: String,
    type: String,
    viewModel: ResultScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val captureController = rememberCaptureController()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val imageUrl = Uri.parse(uri)

    LaunchedEffect(Unit) {
        viewModel.onAction(ResultScreenAction.OnInferImage(imageUrl, context))
        delay(3000) //3s
        viewModel.onAction(ResultScreenAction.OnIsProcessingChanged(false))
    }

    if (state.isProcessing) {
        ProcessingScreen(
            image = imageUrl
        )
    } else {
        ResultScreen(
            captureController = captureController,
            imageUrl = imageUrl,
            imageWidth = state.imageWidth,
            imageHeight = state.imageHeight,
            imageType = type,
            allDetections = state.allDetections,
            detections = state.detections,
            selectedFilter = state.selectedFilter,
            showLabel = state.showLabel,
            onToggleShowLabel = {
                viewModel.onAction(ResultScreenAction.OnChangeShowLabel(it))
            },
            onBack = {
                navController.navigate(NavRoutes.HOME) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            onSave = {
                // Capture content
                scope.launch {
                    val bitmapAsync = captureController.captureAsync()
                    try {
                        val bitmap = bitmapAsync.await()
                        val savedUri = saveBitmapToGallery(context, bitmap.asAndroidBitmap())
                        if (savedUri != null) {
                            Toast.makeText(context, "Saved to Gallery!", Toast.LENGTH_SHORT).show()
                        }

                    } catch (error: Throwable) {
                        // Error occurred, do something.
                        Log.e("Error in create bitmap: ", error.toString())
                    }
                }
            },
            onShare = {
                // Capture content
                scope.launch {
                    val bitmapAsync = captureController.captureAsync()
                    try {
                        val bitmap = bitmapAsync.await()
                        shareBitmap(context, bitmap.asAndroidBitmap())
                    } catch (error: Throwable) {
                        // Error occurred, do something.
                        Log.e("Error in create bitmap: ", error.toString())
                    }
                }
            },
            expandedIds = state.expandedIds,
            onToggleExpand = {
                viewModel.onAction(ResultScreenAction.OnToggleExpand(it))
            },
            onFilterChanged = {
                viewModel.onAction(ResultScreenAction.OnFilterChanged(it))
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResultScreen(
    captureController: CaptureController,
    imageUrl: Uri,
    imageType: String,
    imageWidth: Int,
    imageHeight: Int,
    showLabel: Boolean,
    expandedIds: Set<String>,
    allDetections: List<Detection>,
    detections: List<Detection>,
    selectedFilter: String,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit,
    onToggleShowLabel: (Boolean) -> Unit,
    onToggleExpand: (String) -> Unit,
    onFilterChanged: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    val detectionCounts = allDetections.groupingBy { it.clazz }.eachCount()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        /** ---------------- HEADER ---------------- */
        Surface(
            tonalElevation = 1.dp,
            shadowElevation = 2.dp,
            modifier = Modifier.background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }

                    Column {
                        Text("Detection Results", color = Color(0xFF111827))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFFF3F4F6),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = if (imageType == "thermal") "🔥 Thermal" else "📷 Color",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "${allDetections.size} vehicles",
                                color = Color(0xFF6B7280),
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Row {
                    IconButton(
                        onClick = onSave,
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null)
                    }
                    IconButton(
                        onClick = onShare,
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null)
                    }
                }
            }
        }

        /** ---------------- FILTER BAR ---------------- */
        HorizontalDivider(thickness = 1.dp)

        Surface(
            color = Color(0xFFF9FAFB),
            tonalElevation = 1.dp
        ) {
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                FilterChip(
                    label = "All (${allDetections.size})",
                    isSelected = selectedFilter == "all",
                    onClick = { onFilterChanged("all") }
                )

                Spacer(Modifier.width(6.dp))

                VehicleType.entries.forEach { type ->
                    val count = detectionCounts[type] ?: 0
                    if (count > 0) {
                        FilterChip(
                            label = "${
                                type.name.lowercase().replace('_', ' ')
                                    .replaceFirstChar { it.uppercase() }
                            } ($count)",
                            isSelected = selectedFilter == type.name.lowercase(),
                            onClick = { onFilterChanged(type.name.lowercase()) }
                        )
                        Spacer(Modifier.width(6.dp))
                    }
                }
            }
        }

        HorizontalDivider(thickness = 1.dp)


        /** ---------------- CONTENT AREA ---------------- */
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            var zoomState by remember { mutableStateOf(ZoomState()) }

            val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
                zoomState = zoomState.copy(
                    scale = (zoomState.scale * zoomChange).coerceIn(1f, 6f),
                    offset = zoomState.offset + panChange
                )
            }

            val doubleTapZoom: (Offset) -> Unit = { tapOffset ->
                zoomState = if (zoomState.scale < 2f) {
                    ZoomState(
                        scale = 2.5f,
                        offset = zoomState.offset + (tapOffset - Offset.Zero) * 1.0f
                    )
                } else {
                    ZoomState()
                }
            }

            fun zoomIn() {
                zoomState = zoomState.copy(
                    scale = (zoomState.scale + 0.5f).coerceIn(1f, 6f)
                )
            }

            fun zoomOut() {
                zoomState = zoomState.copy(
                    scale = (zoomState.scale - 0.5f).coerceIn(1f, 6f),
                    offset = Offset.Zero.takeIf { zoomState.scale <= 1.1f } ?: zoomState.offset
                )
            }


            /** ----- IMAGE VIEW WITH ZOOM  ----- */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .aspectRatio(3f / 4f)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .capturable(captureController)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = { offset -> doubleTapZoom(offset) }
                                )
                            }
                            .graphicsLayer {
                                scaleX = zoomState.scale
                                scaleY = zoomState.scale
                                translationX = zoomState.offset.x
                                translationY = zoomState.offset.y
                            }
                            .transformable(transformableState)
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Detection result",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )

                        DetectionOverlay(
                            detections = detections,
                            imageWidth = imageWidth,
                            imageHeight = imageHeight,
                            showLabel = showLabel,
                            expandedIds = expandedIds,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Zoom controls (mock)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SmallIconButton(icon = Icons.Default.ZoomOut, onClick = { zoomOut() })
                        SmallIconButton(icon = Icons.Default.ZoomIn, onClick = { zoomIn() })
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            HorizontalDivider(thickness = 1.dp)

            LabelToggleOverlay(
                showLabel = showLabel,
                onToggle = onToggleShowLabel
            )

            /** ----- DETECTION LIST ----- */
            DetectionList(
                detections = detections,
                expandedIds = expandedIds,
                onToggleExpand = { onToggleExpand(it) }
            )
        }
    }
}

@Composable
fun LabelToggleOverlay(
    showLabel: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Show Label",
            color = Color(0xFF111827),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = showLabel,
            onCheckedChange = { onToggle(!showLabel) }
        )
    }
}


@Composable
fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) Color(0xFF2563EB) else Color.White,
        border = BorderStroke(1.dp, Color(0xFFD1D5DB)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color(0xFF111827),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 13.sp
        )
    }
}

@Composable
fun SmallIconButton(icon: ImageVector, onClick: () -> Unit) {
    Surface(
        shape = CircleShape,
        tonalElevation = 1.dp,
        modifier = Modifier
            .size(36.dp)
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Color(0xFF111827))
        }
    }
}


@Preview
@Composable
fun ResultScreenPreview() {
    VehicleDetectionTheme {
        ResultScreen(
            captureController = CaptureController(),
            onBack = {},
            imageUrl = Uri.parse(""),
            imageType = "",
            allDetections = listOf(),
            detections = listOf(),
            selectedFilter = "all",
            onSave = {},
            onShare = {},
            onFilterChanged = {},
            imageWidth = 0,
            imageHeight = 0,
            showLabel = true,
            onToggleShowLabel = {},
            expandedIds = setOf(),
            onToggleExpand = {}
        )
    }
}
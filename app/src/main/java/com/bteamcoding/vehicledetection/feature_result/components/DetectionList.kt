package com.bteamcoding.vehicledetection.feature_result.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bteamcoding.vehicledetection.core.data.mockDetections
import com.bteamcoding.vehicledetection.core.domain.model.Detection
import com.bteamcoding.vehicledetection.ui.theme.VehicleDetectionTheme

@Composable
fun DetectionList(
    detections: List<Detection>,
    expandedIds: Set<String>,
    onToggleExpand: (String) -> Unit
) {

    if (detections.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No vehicles detected with current filter",
                    color = Color(0xFF6B7280)
                )
            }
        }
        return
    }

    Column(
//        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Detected Vehicles",
            color = Color(0xFF111827),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            detections.forEachIndexed { index, detection ->

                val isExpanded = expandedIds.contains(detection.id)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Avatar icon background
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        CLASS_COLORS_COMPOSE[detection.clazz]!!,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(CLASS_ICONS_COMPOSE[detection.clazz]!!, fontSize = 24.sp)
                            }

                            Column(modifier = Modifier.weight(1f)) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = detection.clazz.name.lowercase().replace("_", " "),
                                        color = Color(0xFF111827),
                                        style = MaterialTheme.typography.bodyLarge
                                    )

                                    // Badge
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Color(0xFFE5E7EB),
                                                RoundedCornerShape(6.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "#${index + 1}",
                                            fontSize = 11.sp,
                                            color = Color(0xFF111827)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Confidence Progress Bar
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(8.dp)
                                            .background(Color(0xFFE5E7EB), RoundedCornerShape(50))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth(detection.confidence.toFloat() / 100f)
                                                .background(
                                                    Color(0xFF22C55E),
                                                    RoundedCornerShape(50)
                                                )
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "${detection.confidence}%",
                                        fontFamily = FontFamily.Monospace,
                                        color = Color(0xFF111827)
                                    )
                                }
                            }

                            IconButton(onClick = { onToggleExpand(detection.id) }) {
                                Icon(
                                    imageVector = if (isExpanded)
                                        Icons.Default.KeyboardArrowUp
                                    else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth()
                            ) {
                                HorizontalDivider(color = Color(0xFFE5E7EB))

                                Text(
                                    "Bounding Box Coordinates:",
                                    color = Color(0xFF374151),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                ConstraintLayout(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFF3F4F6), RoundedCornerShape(10.dp))
                                        .padding(12.dp)
                                ) {
                                    val guideline = createGuidelineFromStart(0.5f)

                                    val (x, y, w, h) = createRefs()

                                    BboxRow("X", detection.bbox.x,
                                        modifier = Modifier.constrainAs(x) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                        })
                                    BboxRow(
                                        "Y",
                                        detection.bbox.y,
                                        modifier = Modifier.constrainAs(y) {
                                            top.linkTo(parent.top)
                                            start.linkTo(guideline)
                                        })
                                    BboxRow(
                                        "Width",
                                        detection.bbox.width,
                                        modifier = Modifier.constrainAs(w) {
                                            top.linkTo(x.bottom, margin = 4.dp)
                                            start.linkTo(parent.start)
                                        })
                                    BboxRow(
                                        "Height",
                                        detection.bbox.height,
                                        modifier = Modifier.constrainAs(h) {
                                            top.linkTo(y.bottom, margin = 4.dp)
                                            start.linkTo(guideline)
                                        })

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BboxRow(label: String, value: Number, modifier: Modifier) {
    Row(modifier = modifier) {
        Text("$label: ", color = Color(0xFF6B7280), fontFamily = FontFamily.Monospace)
        Text(value.toString(), color = Color(0xFF111827), fontFamily = FontFamily.Monospace)
    }
}


@Composable
@Preview
fun DetectionListPreview() {
    VehicleDetectionTheme {
        DetectionList(
            detections = mockDetections,
            expandedIds = setOf(),
            onToggleExpand = {}
        )
    }
}
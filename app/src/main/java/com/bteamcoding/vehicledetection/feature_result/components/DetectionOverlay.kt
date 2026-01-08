package com.bteamcoding.vehicledetection.feature_result.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.bteamcoding.vehicledetection.core.domain.model.Detection
import com.bteamcoding.vehicledetection.core.domain.model.VehicleType
import kotlin.math.max
import kotlin.math.min

@Composable
fun DetectionOverlay(
    detections: List<Detection>,
    imageWidth: Int,
    imageHeight: Int,
    showLabel: Boolean,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {

        if (imageWidth == 0 || imageHeight == 0) return@Canvas

        val scale = min(
            size.width / imageWidth,
            size.height / imageHeight
        )

        val renderedWidth = imageWidth * scale
        val renderedHeight = imageHeight * scale

        val offsetX = (size.width - renderedWidth) / 2f
        val offsetY = (size.height - renderedHeight) / 2f

        detections.forEach { detection ->
            val box = detection.bbox

            val left = offsetX + box.x * scale
            val top = offsetY + box.y * scale
            val width = box.width * scale
            val height = box.height * scale

            val color = when (detection.clazz) {
                VehicleType.CAR -> Color(0xFF22C55E)
                VehicleType.TRUCK -> Color(0xFF3B82F6)
                VehicleType.BUS -> Color(0xFFF97316)
                VehicleType.VAN -> Color(0xFFA855F7)
                else -> Color.Red
            }

            /* ---------------- Bounding box ---------------- */
            drawRect(
                color = color,
                topLeft = Offset(left, top),
                size = Size(width, height),
                style = Stroke(width = max(2f, 3f * scale))
            )

            /* ---------------- Label ---------------- */
            if (showLabel) {

                val label = "${detection.clazz}: ${(detection.confidence)}%"

                val textSize = max(10f, 12f * scale)
                val padding = 4f * scale
                val labelHeight = textSize + padding * 2

                val labelTop = max(
                    offsetY,
                    top - labelHeight
                )

                // Background
                drawRect(
                    color = color.copy(alpha = 0.85f),
                    topLeft = Offset(left, labelTop),
                    size = Size(
                        width = label.length * textSize * 0.55f,
                        height = labelHeight
                    )
                )

                // Text
                drawContext.canvas.nativeCanvas.drawText(
                    label,
                    left + padding,
                    labelTop + labelHeight - padding,
                    android.graphics.Paint().apply {
                        this.textSize = textSize
                        this.color = android.graphics.Color.WHITE
                        isAntiAlias = true
                        typeface = android.graphics.Typeface.DEFAULT_BOLD
                    }
                )
            }
        }
    }
}

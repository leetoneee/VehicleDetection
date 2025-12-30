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

@Composable
fun DetectionOverlay(
    detections: List<Detection>,
    imageWidth: Int,
    imageHeight: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {

        val scaleX = size.width / imageWidth
        val scaleY = size.height / imageHeight

        detections.forEach { detection ->
            val box = detection.bbox

            val left = box.x * scaleX
            val top = box.y * scaleY
            val width = box.width * scaleX
            val height = box.height * scaleY

            val color = when (detection.clazz) {
                VehicleType.CAR -> Color(0xFF22C55E)
                VehicleType.TRUCK -> Color(0xFF3B82F6)
                VehicleType.BUS -> Color(0xFFF97316)
                VehicleType.VAN -> Color(0xFFA855F7)
                else -> Color.Red
            }

            // Bounding box
            drawRect(
                color = color,
                topLeft = Offset(left, top),
                size = Size(width, height),
                style = Stroke(width = 3.dp.toPx())
            )

            // Label background
//            drawRect(
//                color = color.copy(alpha = 0.85f),
//                topLeft = Offset(left, max(0f, top - 22.dp.toPx())),
//                size = Size(width = 80.dp.toPx(), height = 22.dp.toPx())
//            )

//            // Text
//            drawContext.canvas.nativeCanvas.apply {
//                drawText(
//                    "${detection.clazz.name} ${(detection.confidence * 100).toInt()}%",
//                    left + 6.dp.toPx(),
//                    max(14.dp.toPx(), top - 6.dp.toPx()),
//                    android.graphics.Paint().apply {
//                        textSize = 12.sp.toPx()
//                        color = android.graphics.Color.WHITE
//                        isAntiAlias = true
//                        typeface = android.graphics.Typeface.DEFAULT_BOLD
//                    }
//                )
//            }
        }
    }
}

package com.example.mandirisubmission.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun SquigglyProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
) {
    Canvas(modifier = modifier.height(24.dp)) {
        val width = size.width
        val centerY = size.height / 2
        val strokeWidthPx = 4.dp.toPx()
        
        // Draw Track (Straight line)
        drawLine(
            color = trackColor,
            start = Offset(0f, centerY),
            end = Offset(width, centerY),
            strokeWidth = strokeWidthPx,
            cap = StrokeCap.Round
        )
        
        // Draw dot at the end of the track
        drawCircle(
            color = color,
            radius = strokeWidthPx / 1.2f,
            center = Offset(width, centerY)
        )
        
        // Draw Progress Squiggle
        val progressWidth = width * progress
        if (progressWidth > 0) {
            val path = Path()
            path.moveTo(0f, centerY)
            
            val waveLength = 40f
            val amplitude = 8f
            
            var currentX = 0f
            while (currentX <= progressWidth) {
                val relativeX = currentX / waveLength
                val y = centerY + sin(relativeX.toDouble() * 2 * Math.PI).toFloat() * amplitude
                path.lineTo(currentX, y)
                currentX += 2f
            }
            
            drawPath(
                path = path,
                color = color,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}

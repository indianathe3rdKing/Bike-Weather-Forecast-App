package com.example.bikeweatherforecastapp.presentation.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary
import com.example.bikeweatherforecastapp.ui.theme.ProgressBackground


@Composable
fun CircularProgressBar(
    score: Int, strokeWidth: Int, color: Color,
    modifier: Modifier = Modifier, fontSize: Int = 14
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidth = strokeWidth.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2

            //Background circle
            drawCircle(
                color = ProgressBackground,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )

            //Progress circle
            val sweepAngle = (score / 100f) * 360f
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "$score%",
            color = TextPrimary,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            style= TextStyle(
                shadow = Shadow(
                    color = TextPrimary.copy(0.6f),
                    offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                    blurRadius = 10f
                )
            )
        )
    }
}
package com.knu.cloud.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knu.cloud.R
import kotlin.math.*

@Composable
fun LineChartComponent(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(300.dp) // 크기 제한
                .clip(RoundedCornerShape(16.dp)) // 둥근 테두리 적용
                .background(colorResource(id = R.color.chart_box_color))
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "CPU 사용률(%)",
                    fontSize = 25.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(50.dp))
                LineChart() // data 할당 전
            }
        }
    }
}

private const val animationDuration = 800
// 원 모양
private const val chartDegrees = 360f

@Composable
internal fun LineChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Int, Double>> = emptyList(),
) {
    val spacing = 100f
    val graphColor = Color.Cyan
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = remember { (data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0 }
    val lowerValue = remember { (data.minOfOrNull { it.second }?.toInt() ?: 0) }
    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size
        (data.indices step 5).forEach { i -> // Step 5로 해서 간격 여유두기
            val hour = data[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${hour.toString()}:00",
                    spacing + i * spacePerHour,
                    size.height,
                    textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }

        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val info = data[i]
                val ratio = (info.second - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (ratio * height).toFloat()

                if (i == 0) { moveTo(x1, y1) }
                lineTo(x1, y1)
            }
        }

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(size.width - spacePerHour, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )

    }
}
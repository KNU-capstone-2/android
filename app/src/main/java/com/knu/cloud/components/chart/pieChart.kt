package com.knu.cloud.components.chart

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import com.knu.cloud.extensions.toPercent
import kotlin.math.*

private val CARD_SIZE = 140 .dp

@Composable
fun PieChartComponent(
    title: String,
    assignedData: Int,
    remainingData: Int,
) {
    val chartColors = listOf(
        colorResource(id = R.color.pie_red),
        colorResource(id = R.color.pie_remaining),
    )

    val assignedDataFloat: Float = assignedData.toFloat()
    val remainingDataFloat: Float = remainingData.toFloat()

    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(CARD_SIZE).padding(5.dp),
            elevation = 3.dp
        ) {
            PieChart(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(), // Pie 전체 크기 지정
                colors = chartColors,
                assignedDataFloat = assignedDataFloat,
                remainingDataFloat = remainingDataFloat
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = title)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "${assignedData+remainingData} 에 대한 사용됨 ${assignedData}")
    }
}

private const val animationDuration = 800
// 원 모양
private const val chartDegrees = 360f

@Composable
internal fun PieChart(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    assignedDataFloat: Float,
    remainingDataFloat: Float,
    textColor: Color = colorResource(id = R.color.pie_text),
    animated: Boolean = true
) {
    val inputValues = listOf(assignedDataFloat, remainingDataFloat)

    // 시계방향으로 그리기 시작 (위에서 오른쪽으로)
    var startAngle = 270f

    //입력 백분율 계산
    val proportions = inputValues.toPercent()

    // 각 입력 계산 슬라이드 각도
    val angleProgress = proportions.map { prop ->
        chartDegrees * prop / 100
    }

    // 클릭 위치를 처리하기 위해 각 슬라이드 끝점을 각도 단위로 계산
    val progressSize = mutableListOf<Float>()

    LaunchedEffect(angleProgress) {
        progressSize.add(angleProgress.first())
        for (x in 1 until angleProgress.size) {
            progressSize.add(angleProgress[x] + progressSize[x - 1])
        }
    }

    // used for animating each slice
    val pathPortion = remember {
        Animatable(initialValue = 0f)
    }

    // animate chart slices on composition
    LaunchedEffect(inputValues) {
        pathPortion.animateTo(1f, animationSpec = tween(if (animated) animationDuration else 0))
    }

    // text style
    /* Adding click functionality to the pie chart */
    val density = LocalDensity.current
    val textPaint = remember {
        Paint().apply {
            color = textColor.toArgb()
            textSize = 25f
            textAlign = Paint.Align.CENTER
        }
    }

    // Canvas를 사용하여 레이아웃 생성 및 입력 그리기
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {

        val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
        val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
        val canvasSizeDp = with(density) { canvasSize.toDp() }

        Canvas(
            modifier = Modifier
                .size(canvasSizeDp)
                .pointerInput(inputValues) {}
        ) {

            angleProgress.forEachIndexed { index, angle ->
                drawArc(
                    color = colors[index].copy(alpha = 0.4f),
                    startAngle = startAngle,
                    sweepAngle = angle * pathPortion.value,
                    useCenter = true,
                    size = size,
                    style = Fill
                )
                // 섹션의 중심점 계산
                val sectionCenterX = size.width / 2 + size.width / 4 * cos(Math.toRadians((startAngle + angle / 2).toDouble())).toFloat()
                val sectionCenterY = size.height / 2 + size.width / 4 * sin(Math.toRadians((startAngle + angle / 2).toDouble())).toFloat()

                // Text 그리기
                drawContext.canvas.nativeCanvas.drawText(
                    "${proportions[index].roundToInt()}%",
                    sectionCenterX,
                    sectionCenterY,
                    textPaint
                )

                startAngle += angle
            }
        }
    }
}
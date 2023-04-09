package com.knu.cloud.components

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import com.knu.cloud.extensions.toPercent
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Component for creating Donut Chart
 * Slices are painted clockwise
 * e.g. 1st input value starts from top to the right, etc
 */

private const val animationDuration = 800
private const val chartDegrees = 360f // 원 모양

@Composable
fun DonutChartComponent(
    // 인스턴스 현황
    // Current Usage, Added, Remaining 순
    chartValues: List<Int>
) {
    val chartColors = listOf(
        colorResource(id = R.color.instance_current_usage),
        colorResource(id = R.color.instance_added),
        colorResource(id = R.color.instance_remaining),
    )

    val chartValueFloat: List<Float> = chartValues.map { it.toFloat() * 10 }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total Instances")
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "(10 Max)")
        Spacer(modifier = Modifier.height(10.dp))

        DonutChart(
            modifier = Modifier
                .padding(20.dp)
                .size(120.dp), // Donut 전체 크기 지정
            colors = chartColors,
            inputValues = chartValueFloat,
        )

        Box(
            modifier = Modifier
                .padding(5.dp)
                .border(1.dp, Color.Black)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.padding(5.dp)) {
                    Box(modifier = Modifier
                        .padding(end = 10.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.instance_current_usage))){}
                    Text("${chartValues[0]} Current Usage")
                }
                Row(modifier = Modifier.padding(5.dp)){
                    Box(modifier = Modifier
                        .padding(end = 10.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.instance_added))){}
                    Text("${chartValues[1]} Added")
                }
                Row(modifier = Modifier.padding(5.dp)){
                    Box(modifier = Modifier
                        .padding(end = 10.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.instance_remaining))){}
                    Text("${chartValues[2]} Remaining")
                }
            }
        }
    }
}

@Composable
internal fun DonutChart(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    inputValues: List<Float>,
    textColor: Color = Color.Black,
    animated: Boolean = true
) {

    assert(inputValues.isNotEmpty() && inputValues.size == colors.size) {
        "Input values count must be equal to colors size"
    }

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

    val density = LocalDensity.current

    // text style
    val textFontSize = with(density) { 25.dp.toPx() }
    val textPaint = remember {
        Paint().apply {
            color = textColor.toArgb()
            textSize = textFontSize
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
            val sliceWidth = with(density) { 20.dp.toPx() }

            angleProgress.forEachIndexed { index, angle ->
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = angle * pathPortion.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = sliceWidth)
                )
                if (index == 1) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "${proportions[index].roundToInt()}%",
                        (canvasSize / 2) + textFontSize / 4,
                        (canvasSize / 2) + textFontSize / 4,
                        textPaint
                    )
                }
                startAngle += angle
            }
        }
    }
}
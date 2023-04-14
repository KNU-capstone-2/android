package com.knu.cloud.screens.home.setting

import androidx.compose.ui.Modifier
import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round
import kotlin.math.roundToInt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.toSize

@Composable
fun SettingScreen () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        test()
    }
}

@Composable
fun LineChartScreen() {
    val data = listOf(
        Pair(1, 111.45),
        Pair(2, 111.0),
        Pair(3, 113.45),
        Pair(4, 112.25),
        Pair(5, 116.45),
        Pair(6, 118.65),
        Pair(7, 110.15),
        Pair(8, 113.05),
        Pair(9, 114.25),
        Pair(10, 111.85),
        Pair(11, 110.85)
    )
    LineChart(
        data = data,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

@Composable
fun LineChart(
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

@Composable
fun test() {
    var instanceStateExpanded by remember { mutableStateOf(false) }
    var workExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }
    val items = listOf("Item 1", "Item 2", "Item 3")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        backgroundColor = Color.White,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ){
            Row(
                modifier = Modifier
                    .background(Color.LightGray),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "i-0c8cd9b93248e6a68 (test)에 대한 인스턴스 요약",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                TextButton(
                    onClick = { /* 버튼을 눌렀을 때 수행될 동작 */ },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Blue // 텍스트 색상을 파란색으로 지정
                    )
                ) {
                    Text(
                        modifier = Modifier,
                        fontSize = 12.sp,
                        text = "정보"
                    )
                }
            }
            Text(
                text = "less than a minute 전에 업데이트됨",
                fontSize = 15.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = {  },
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = null, tint = Color.Blue)
                }
                Spacer(modifier= Modifier.width(10.dp))

                OutlinedButton(
                    onClick = {  },
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "연결")
                    }
                }
                Spacer(modifier= Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            fieldSize = coordinates.size.toSize()
                        }
                ) {
                    OutlinedButton(
                        onClick = { instanceStateExpanded = !instanceStateExpanded },
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, Color.Black),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "인스턴스 상태")
                            if (instanceStateExpanded) {
                                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null, tint = Color.Blue)
                            } else {
                                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null)
                            }
                        }
                    }

                    DropdownMenu(
                        expanded = instanceStateExpanded,
                        onDismissRequest = { instanceStateExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { fieldSize.width.toDp() })
                            .height(240.dp)
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                selectedIndex = index
                                instanceStateExpanded = false
                            }) {
                                Text(text = s)
                            }
                        }
                    }
                }
            }
        }
    }
}
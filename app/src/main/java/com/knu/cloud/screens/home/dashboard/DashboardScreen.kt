package com.knu.cloud.screens.home.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.knu.cloud.components.PieChartComponent

@Composable
fun DashBoardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .wrapContentSize(Alignment.Center)
    ) {
        PieChartComponent(
            // Current Usage, Added, Remaining 순
            chartValues = listOf(1, 2, 7)
        )
    }
}

@Preview (showBackground = true)
@Composable
fun HomeScreenPrev() {
//    ProjectHomeScreen()

}
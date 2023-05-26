package com.knu.cloud.components.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.knu.cloud.components.chart.PieChartComponent
import com.knu.cloud.model.home.dashboard.DashboardData

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    type: String,
    dataSet: List<DashboardData>
) {
    Column( modifier= modifier.fillMaxSize()) {
        CategoryTitle(type)
        LazyRow(
            contentPadding = PaddingValues(
                horizontal = 5.dp
            )
        ) {
            items(dataSet) {data ->
                PieChartComponent(
                    title = data.title,
                    assignedData = data.assignedData,
                    remainingData = data.remainingData
                )
            }
        }
    }
}
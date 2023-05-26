package com.knu.cloud.model.home.dashboard

data class DashboardData(
    val title: String,
    val assignedData: Int,
    val remainingData: Int
)

data class DashboardDataSet(
    val computeDataSet: List<DashboardData>,
    val volumeDataSet: List<DashboardData>,
    val networkDataSet: List<DashboardData>
)
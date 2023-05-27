package com.knu.cloud.screens.home.dashboard

import com.knu.cloud.model.home.dashboard.DashboardDataSet
import com.knu.cloud.model.home.dashboard.DashboardUsageData
import com.knu.cloud.model.home.dashboard.DashboardUsageResponse

sealed class DashboardState {
    object Loading: DashboardState()
    data class Success(
        val dataSet: DashboardDataSet,
        val usageDataSet: DashboardUsageData
    ): DashboardState()
    object Error: DashboardState()
}
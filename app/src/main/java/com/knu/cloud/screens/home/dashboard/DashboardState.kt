package com.knu.cloud.screens.home.dashboard

import com.knu.cloud.model.home.dashboard.DashboardDataSet

sealed class DashboardState {
    object Loading: DashboardState()
    data class Success(
        val dataSet: DashboardDataSet
    ): DashboardState()
    object Error: DashboardState()
}
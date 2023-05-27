package com.knu.cloud.repository.home.dashboard

import com.knu.cloud.model.home.dashboard.DashboardResponse
import com.knu.cloud.model.home.dashboard.DashboardUsageResponse

interface DashboardRepository {
    suspend fun getDashboardData(): Result<DashboardResponse?>

    suspend fun getDashboardUsageData(): Result<DashboardUsageResponse?>
}
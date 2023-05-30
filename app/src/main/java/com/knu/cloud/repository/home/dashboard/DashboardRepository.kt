package com.knu.cloud.repository.home.dashboard

import com.knu.cloud.model.home.dashboard.DashboardClass
import com.knu.cloud.model.home.dashboard.DashboardUsageResponse

interface DashboardRepository {
    suspend fun getDashboardData(): Result<DashboardClass?>

//    suspend fun getDashboardUsageData(): Result<DashboardUsageResponse?>
}
package com.knu.cloud.repository.home.dashboard

import com.knu.cloud.model.home.dashboard.DashboardResponse

interface DashboardRepository {
    suspend fun getDashboardData(): Result<DashboardResponse?>
}
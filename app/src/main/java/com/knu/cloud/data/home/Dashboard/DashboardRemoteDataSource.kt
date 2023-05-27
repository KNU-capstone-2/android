package com.knu.cloud.data.home.Dashboard

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.home.dashboard.DashboardResponse
import com.knu.cloud.model.home.dashboard.DashboardUsageResponse
import com.knu.cloud.network.DashboardApiService
import timber.log.Timber
import javax.inject.Inject

class DashboardRemoteDataSource @Inject constructor(
    private val DashboardApiService : DashboardApiService
) {
    suspend fun getDashboardData(): NetworkResult<OpenstackResponse<DashboardResponse>> {
        Timber.tag("network").d("DashboardRemoteDataSource getDashboardData() 호출")
        return DashboardApiService.getDashboardData()
    }

    suspend fun getDashboardUsageData(): NetworkResult<OpenstackResponse<DashboardUsageResponse>> {
        Timber.tag("network").d("DashboardRemoteDataSource getDashboardUsageData() 호출")
        return DashboardApiService.getDashboardUsageData()
    }
}

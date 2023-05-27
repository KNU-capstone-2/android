package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.home.dashboard.DashboardResponse
import com.knu.cloud.model.home.dashboard.DashboardUsageData
import com.knu.cloud.model.home.dashboard.DashboardUsageResponse
import retrofit2.http.GET

interface DashboardApiService {

    @GET("/project")
    suspend fun getDashboardData() : NetworkResult<OpenstackResponse<DashboardResponse>>

    @GET("/TEST")
    suspend fun getDashboardUsageData() : NetworkResult<OpenstackResponse<DashboardUsageResponse>>
}
package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.home.dashboard.DashboardResponse
import retrofit2.http.GET

interface DashboardApiService {

    @GET("/TEST")
    suspend fun getDashboardData() : NetworkResult<OpenstackResponse<DashboardResponse>>

}
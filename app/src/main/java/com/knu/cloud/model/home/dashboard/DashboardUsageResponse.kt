package com.knu.cloud.model.home.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardUsageData(
    val activeInstance: String,
    val useRAM: String,
    val VCPUTime: String,
    val GBTime: String,
    val RAMTime: String,
)

data class DashboardUsageResponse(
    @SerializedName("info")
    val dashboardUsageData: DashboardUsageData
)

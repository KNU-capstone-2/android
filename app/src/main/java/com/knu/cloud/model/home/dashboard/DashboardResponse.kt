package com.knu.cloud.model.home.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("dashboard")
    val dashboardDataSet: DashboardDataSet
)
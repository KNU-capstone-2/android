package com.knu.cloud.model.home.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardClass(
    val instanceCount: Int,
    val vcpuCount: Int,
    val ramCount: Int,
    val volumeCount: Int,
    val snapshotCount: Int,
    val volumeStorageGB: Int,
    val floatingIpCount: Int,
    val securityGroupCount: Int,
    val networkCount: Int,
)

//data class DashboardResponse(
//    @SerializedName("info")
//    val dashboardDataClass: DashboardClass
//)
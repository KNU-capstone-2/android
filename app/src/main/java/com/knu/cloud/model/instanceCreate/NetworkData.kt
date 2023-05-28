package com.knu.cloud.model.instanceCreate

import com.google.gson.annotations.SerializedName

data class NetworkData(
    @SerializedName("name") // 이름
    val network: String,
    @SerializedName("subnets") // 서브넷
    val subNet: String,
    @SerializedName("external") // 외부성
    val state: String,
    @SerializedName("shared") // 공유
    val share: String,
    @SerializedName("adminState") // 관리자 상태
    val adminState: String,
)

data class NetworksResponse(
    @SerializedName("networks")
    val networks : List<NetworkData>
)
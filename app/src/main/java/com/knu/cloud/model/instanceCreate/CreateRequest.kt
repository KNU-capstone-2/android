package com.knu.cloud.model.instanceCreate

import com.google.gson.annotations.SerializedName

data class CreateRequest(
    @SerializedName("serverName")
    val serverName: String = "testJack",
    @SerializedName("imageName")
    val imageName: String = "cirros-0.5.2-x86_64-disk",
    @SerializedName("flavorName")
    val flavorName: String = "m1.micro",
    @SerializedName("networkName")
    val networkName: String = "private",
    @SerializedName("keypairName")
    val keypairName: String = "test123",
)
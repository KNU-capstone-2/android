package com.knu.cloud.model.home.instance

import com.google.gson.annotations.SerializedName

data class InstanceControlResponse(
    @SerializedName("success")
    val isSuccess : Boolean,
    val message : String
)
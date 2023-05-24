package com.knu.cloud.model

import com.google.gson.annotations.SerializedName

data class OpenstackResponse<T>(
    @SerializedName("data")
    val data : T,
)
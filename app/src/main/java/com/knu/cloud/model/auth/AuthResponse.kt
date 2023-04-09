package com.knu.cloud.model.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse<T>(
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val data : T?
)
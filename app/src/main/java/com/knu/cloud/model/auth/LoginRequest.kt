package com.knu.cloud.model.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("id")
    val id : String,
    @SerializedName("password")
    val password : String
)

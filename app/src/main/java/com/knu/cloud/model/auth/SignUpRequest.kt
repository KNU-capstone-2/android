package com.knu.cloud.model.auth

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    val email : String,
    @SerializedName("password")
    val password : String,
)

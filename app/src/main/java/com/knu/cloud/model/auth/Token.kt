package com.knu.cloud.model.auth

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("Set-Cookie")
    val sessionId : String
) {
    constructor() : this("")
}

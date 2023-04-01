package com.knu.cloud.model.auth

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("sessionId")
    val sessionId : String
) {
    constructor() : this("")
}

package com.knu.cloud.model.keypair

import com.google.gson.annotations.SerializedName

data class KeypairCreateRequest(
    @SerializedName("keypairName")
    val name: String,
    @SerializedName("keypairType")
    val type: String,
)

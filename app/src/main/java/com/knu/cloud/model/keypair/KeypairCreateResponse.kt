package com.knu.cloud.model.keypair

import com.google.gson.annotations.SerializedName

data class KeypairCreateResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("fingerprint")
    val fingerprint : String,
    @SerializedName("privateKey")
    val privateKey : String
)
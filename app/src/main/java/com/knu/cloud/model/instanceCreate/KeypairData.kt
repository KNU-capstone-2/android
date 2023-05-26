package com.knu.cloud.model.instanceCreate

import com.google.gson.annotations.SerializedName

data class KeypairData(
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("fingerprint")
    val fingerprint : String
)

data class KeypairsResponse(
    @SerializedName("keypairs")
    val keypairs : List<KeypairData>
)
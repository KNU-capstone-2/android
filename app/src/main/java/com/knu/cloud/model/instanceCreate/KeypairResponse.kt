package com.knu.cloud.model.instanceCreate

import com.google.gson.annotations.SerializedName

data class KeypairResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
)

data class KeypairsResponse(
    @SerializedName("keypairs")
    val keypairs : List<KeypairResponse>
)
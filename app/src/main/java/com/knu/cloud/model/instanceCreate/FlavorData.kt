package com.knu.cloud.model.instanceCreate

import com.google.gson.annotations.SerializedName

data class FlavorData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("vcpus")
    val vcpus: Int,
    @SerializedName("ram")
    val ram: Int,
    @SerializedName("disk")
    val diskTotal: Int,
    @SerializedName("rootDisk")
    val rootDisk: Int,
    @SerializedName("ephemeral")
    val ephemeralDisk: Int,
    @SerializedName("visible")
    val public: String
)

data class FlavorsResponse(
    @SerializedName("flavors")
    val flavors : List<FlavorData>
)
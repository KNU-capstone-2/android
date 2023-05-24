package com.knu.cloud.model.instance

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InstanceData(
    val instancesId: String,
    val instancesName: String,
    val publicIPv4Address: String,
    val privateIPv4Address: String,
    val instanceState: String,
    val publicIPv4DNS: String,
    val hostNameType: String,
    val privateIpDnsName: String,
    val instanceType: String,
    val statusCheck : String,
): Parcelable

data class InstancesResponse(
    @SerializedName("servers")
    val instances : List<InstanceData>
)
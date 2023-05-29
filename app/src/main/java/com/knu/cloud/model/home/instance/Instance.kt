package com.knu.cloud.model.home.instance

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
data class InstancesResponse(
    @SerializedName("instances")
    val instances : List<InstanceData>
)
@Parcelize
data class InstanceData(
    val instanceId: String,
    val instanceName: String,
    val instanceStatus: String,
    val hostNameType: String,
    val securityGroups : String,
    val instanceType: String,
    val createdDate : String,
    val networkName : String,
    val networkAddresses : String,
    val keypairName : String,
    val powerState : String,
    val taskState : String,
    val imageName : String,
): Parcelable

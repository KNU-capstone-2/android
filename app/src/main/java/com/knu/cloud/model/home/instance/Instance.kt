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
    val id : String = "test",
    val creatorName : String = "tester",
    val instanceId: String = "dummy",
    val instanceName: String = " dummy",
    val instanceStatus: String = "ACTIVE",
    val hostNameType: String = "dummy",
    val securityGroups : String = "dummy",
    val instanceType: String = "dummy",
    val createdDate : String = "2023-05-29T13:32:35Z",
    val networkName : String = "dummy",
    val networkAddresses : String = "fdef:e3ae:13d0:0:f816:3eff:fe2b:6cd6 10.0.0.28",
    val keypairName : String = "test123",
    val powerState : String = "Running",
    val taskState : String = "None",
    val imageName : String = "dummy",
): Parcelable


val testInstanceDataList = listOf<InstanceData>(
        InstanceData(),InstanceData(),InstanceData(),InstanceData()
)
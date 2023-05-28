package com.knu.cloud.screens.instanceCreate

import com.knu.cloud.model.instanceCreate.FlavorData
import com.knu.cloud.model.instanceCreate.ImageData
import com.knu.cloud.model.instanceCreate.KeypairData
import com.knu.cloud.model.instanceCreate.NetworkData

data class InstanceCreateDetailUiState(
    val projectName :String = "demo",
    val instanceName : String = "",
    val description : String = "",
    val availabilityZone : String = "Nova",
    val addCount : Int = 1,                                     // 개수, 값이 바뀔때마다 Total Instances차트의 Added도 업데이트 된다
    val currentCount : Int = 0,
    val totalCount : Int = 10                                             // remaining을 구하기 위함
)

data class InstanceCreateSourceUiState(
    val selectedTitle : String = "Image",
    val volumeSize : Int = 1,
    val uploadSource : ImageData? = null,
    val uploadSourceIndex : Int = 0,
    val possibleSources : List<ImageData> = emptyList()
)

data class InstanceCreateFlavorUiState(
    val uploadFlavor : FlavorData? = null,
    val uploadFlavorIndex : Int = 0,
    val possibleFlavors : List<FlavorData> = emptyList()
)

data class InstanceCreateNetworkUiState(
    val uploadNetwork : NetworkData? = null,
    val uploadNetworkIndex : Int = 0,
    val possibleNetworks : List<NetworkData> = emptyList()
)

data class InstanceCreateKeypairUiState(
    val uploadKeypair : KeypairData? = null,
    val uploadKeypairIndex : Int = 0,
    val possibleKeypairs : List<KeypairData> = emptyList()
)
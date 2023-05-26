package com.knu.cloud.repository.instanceCreate

import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*

interface InstanceCreateRepository {
    suspend fun getAllFlavorData(): Result<FlavorsResponse?>
    suspend fun getAllKeypairData(): Result<KeypairsResponse?>
    suspend fun getAllNetworkData(): Result<NetworksResponse?>
    suspend fun getAllSourceData(): Result<SourcesResponse?>

    suspend fun createInstance(createRequest: CreateRequest) : Result<InstanceData?>
}
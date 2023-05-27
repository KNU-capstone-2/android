package com.knu.cloud.repository.instanceCreate

import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*

interface InstanceCreateRepository {
    suspend fun getAllFlavorData(): Result<List<FlavorData>?>
    suspend fun getAllKeypairData(): Result<List<KeypairData>?>
    suspend fun getAllNetworkData(): Result<List<NetworkData>?>
    suspend fun getAllSourceData(): Result<List<ImageData>?>

    suspend fun createInstance(createRequest: CreateRequest) : Result<InstanceData?>
}
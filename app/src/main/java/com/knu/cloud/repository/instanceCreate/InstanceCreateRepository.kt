package com.knu.cloud.repository.instanceCreate

import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*

interface InstanceCreateRepository {
    suspend fun getAllFlavorData(): Result<List<FlavorResponse>?>
    suspend fun getAllKeypairData(): Result<List<KeypairResponse>?>
    suspend fun getAllNetworkData(): Result<List<NetworkResponse>?>
    suspend fun getAllSourceData(): Result<List<SourceResponse>?>

    suspend fun createInstance(createRequest: CreateRequest) : Result<InstanceData?>
}
package com.knu.cloud.repository.instanceCreate

import com.knu.cloud.model.instanceCreate.FlavorResponse
import com.knu.cloud.model.instanceCreate.KeypairResponse
import com.knu.cloud.model.instanceCreate.NetworkResponse
import com.knu.cloud.model.instanceCreate.SourceResponse

interface InstanceCreateRepository {
    suspend fun getAllFlavorData(): Result<List<FlavorResponse>?>
    suspend fun getAllKeypairData(): Result<List<KeypairResponse>?>
    suspend fun getAllNetworkData(): Result<List<NetworkResponse>?>
    suspend fun getAllSourceData(): Result<List<SourceResponse>?>
}
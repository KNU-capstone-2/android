package com.knu.cloud.data.instanceCreate

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.instanceCreate.FlavorResponse
import com.knu.cloud.model.instanceCreate.KeypairResponse
import com.knu.cloud.model.instanceCreate.NetworkResponse
import com.knu.cloud.model.instanceCreate.SourceResponse
import com.knu.cloud.network.InstanceCreateApiService
import javax.inject.Inject

class InstanceCreateDataSource @Inject constructor(
    private val instanceCreateApiService: InstanceCreateApiService
) {
    suspend fun getFlavorData() : NetworkResult<AuthResponse<List<FlavorResponse>>> {
        return instanceCreateApiService.getFlavorData()
    }

    suspend fun getKeypairData() : NetworkResult<AuthResponse<List<KeypairResponse>>> {
        return instanceCreateApiService.getKeypairData()
    }

    suspend fun getNetworkData() : NetworkResult<AuthResponse<List<NetworkResponse>>> {
        return instanceCreateApiService.getNetworkData()
    }

    suspend fun getSourceData() : NetworkResult<AuthResponse<List<SourceResponse>>> {
        return instanceCreateApiService.getSourceData()
    }
}

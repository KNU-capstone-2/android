package com.knu.cloud.data.instanceCreate

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import com.knu.cloud.network.InstanceCreateApiService
import javax.inject.Inject

class InstanceCreateDataSource @Inject constructor(
    private val instanceCreateApiService: InstanceCreateApiService
) {
    suspend fun createInstance(createRequest: CreateRequest) :NetworkResult<AuthResponse<InstanceData>>{
        return instanceCreateApiService.instanceCreate(createRequest)
    }
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

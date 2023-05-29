package com.knu.cloud.data.instanceCreate

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import com.knu.cloud.network.apiService.InstanceCreateApiService
import javax.inject.Inject

class InstanceCreateRemoteDataSource @Inject constructor(
    private val instanceCreateApiService: InstanceCreateApiService
) {
    suspend fun createInstance(createRequest: CreateRequest) :NetworkResult<AuthResponse<InstanceData>>{
        return instanceCreateApiService.instanceCreate(createRequest)
    }
    suspend fun getFlavorData() : NetworkResult<AuthResponse<List<FlavorData>>> {

        return instanceCreateApiService.getFlavorData()
    }

    suspend fun getKeypairData() : NetworkResult<AuthResponse<List<KeypairData>>> {
        return instanceCreateApiService.getKeypairData()
    }

    suspend fun getNetworkData() : NetworkResult<AuthResponse<List<NetworkData>>> {
        return instanceCreateApiService.getNetworkData()
    }

    suspend fun getImages() : NetworkResult<AuthResponse<List<ImageData>>> {
        return instanceCreateApiService.getImages()
    }
}

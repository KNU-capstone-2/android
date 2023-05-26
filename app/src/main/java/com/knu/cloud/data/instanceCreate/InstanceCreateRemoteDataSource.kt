package com.knu.cloud.data.instanceCreate

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import com.knu.cloud.network.InstanceCreateApiService
import javax.inject.Inject

class InstanceCreateRemoteDataSource @Inject constructor(
    private val instanceCreateApiService: InstanceCreateApiService
) {
    suspend fun createInstance(createRequest: CreateRequest) :NetworkResult<AuthResponse<InstanceData>>{
        return instanceCreateApiService.instanceCreate(createRequest)
    }
    suspend fun getFlavorData() : NetworkResult<OpenstackResponse<FlavorsResponse>> {
        return instanceCreateApiService.getFlavorData()
    }

    suspend fun getKeypairData() : NetworkResult<OpenstackResponse<KeypairsResponse>> {
        return instanceCreateApiService.getKeypairData()
    }

    suspend fun getNetworkData() : NetworkResult<OpenstackResponse<NetworksResponse>> {
        return instanceCreateApiService.getNetworkData()
    }

    suspend fun getImages() : NetworkResult<OpenstackResponse<ImagesResponse>> {
        return instanceCreateApiService.getImages()
    }
}

package com.knu.cloud.network.apiService

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InstanceCreateApiService {

    @GET("/flavors")
    suspend fun getFlavorData(): NetworkResult<AuthResponse<List<FlavorData>>>

    @GET("/keypairs")
    suspend fun getKeypairData(): NetworkResult<AuthResponse<List<KeypairData>>>

    @GET("/networks")
    suspend fun getNetworkData(): NetworkResult<AuthResponse<List<NetworkData>>>

    @GET("/images")
    suspend fun getImages(): NetworkResult<AuthResponse<List<ImageData>>>

    @POST("/instance")
    suspend fun instanceCreate(
        @Body createRequest: CreateRequest
    ) : NetworkResult<AuthResponse<InstanceData>>
}
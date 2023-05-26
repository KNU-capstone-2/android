package com.knu.cloud.network

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
    suspend fun getFlavorData(): NetworkResult<OpenstackResponse<FlavorsResponse>>

    @GET("/keypairs")
    suspend fun getKeypairData(): NetworkResult<OpenstackResponse<KeypairsResponse>>

    @GET("/networks")
    suspend fun getNetworkData(): NetworkResult<OpenstackResponse<NetworksResponse>>

    @GET("/images")
    suspend fun getImages(): NetworkResult<OpenstackResponse<ImagesResponse>>

    @POST("/api/v1/create")
    suspend fun instanceCreate(
        @Body createRequest: CreateRequest
    ) : NetworkResult<AuthResponse<InstanceData>>
}
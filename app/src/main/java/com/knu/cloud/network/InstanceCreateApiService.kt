package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.Token
import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InstanceCreateApiService {

    @GET("TEST")
    suspend fun getFlavorData(): NetworkResult<AuthResponse<List<FlavorResponse>>>

    @GET("TEST")
    suspend fun getKeypairData(): NetworkResult<AuthResponse<List<KeypairResponse>>>

    @GET("TEST")
    suspend fun getNetworkData(): NetworkResult<AuthResponse<List<NetworkResponse>>>

    @GET("TEST")
    suspend fun getSourceData(): NetworkResult<AuthResponse<List<SourceResponse>>>

    @POST("/api/v1/create")
    suspend fun instanceCreate(
        @Body createRequest: CreateRequest
    ) : NetworkResult<AuthResponse<InstanceData>>
}
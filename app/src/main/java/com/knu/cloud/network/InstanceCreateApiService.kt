package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.instanceCreate.FlavorResponse
import com.knu.cloud.model.instanceCreate.KeypairResponse
import com.knu.cloud.model.instanceCreate.NetworkResponse
import com.knu.cloud.model.instanceCreate.SourceResponse
import retrofit2.http.GET

interface InstanceCreateApiService {

    @GET("TEST")
    suspend fun getFlavorData(): NetworkResult<AuthResponse<List<FlavorResponse>>>

    @GET("TEST")
    suspend fun getKeypairData(): NetworkResult<AuthResponse<List<KeypairResponse>>>

    @GET("TEST")
    suspend fun getNetworkData(): NetworkResult<AuthResponse<List<NetworkResponse>>>

    @GET("TEST")
    suspend fun getSourceData(): NetworkResult<AuthResponse<List<SourceResponse>>>
}
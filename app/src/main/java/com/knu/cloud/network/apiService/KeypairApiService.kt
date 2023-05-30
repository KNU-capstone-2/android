package com.knu.cloud.network.apiService

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.CreateRequest
import com.knu.cloud.model.instanceCreate.KeypairData
import com.knu.cloud.model.instanceCreate.KeypairsResponse
import com.knu.cloud.model.keypair.KeypairCreateRequest
import com.knu.cloud.model.keypair.KeypairCreateResponse
import retrofit2.http.*

interface KeypairApiService {

    @GET("/keypairs")
    suspend fun getKeypairs(): NetworkResult<AuthResponse<List<KeypairData>>>

    @GET("/keypair/{id}")
    suspend fun getKeypair(
        @Path("id") keypairName : String
    ) : NetworkResult<AuthResponse<KeypairData>>

    @POST("/keypair")
    suspend fun createKeypair(
        @Body keypairCreateRequest: KeypairCreateRequest
    ) : NetworkResult<AuthResponse<KeypairCreateResponse>>

    @DELETE("/keypair/{id}")
    suspend fun deleteKeypair(
        @Path("id") keypairName: String
    ): NetworkResult<AuthResponse<String>>

}
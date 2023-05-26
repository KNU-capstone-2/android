package com.knu.cloud.network

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
    suspend fun getKeypairs(): NetworkResult<OpenstackResponse<KeypairsResponse>>

    @GET("/keypairs/{id}")
    suspend fun getKeypair(
        @Path("id") keypairName : String
    ) : NetworkResult<KeypairData>

    @POST("/keypairs")
    suspend fun createKeypair(
        @Body keypairCreateRequest: KeypairCreateRequest
    ) : NetworkResult<KeypairCreateResponse>

    @DELETE("/keypairs/{id}")
    suspend fun deleteKeypair(
        @Path("id") keypairName: String
    ): NetworkResult<String>

}
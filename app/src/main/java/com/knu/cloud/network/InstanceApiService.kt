package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.SignUpRequest
import com.knu.cloud.model.auth.Token
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.home.instance.InstancesResponse
import retrofit2.http.*

interface InstanceApiService {

    @GET("/servers")
    suspend fun getAllInstances() : NetworkResult<OpenstackResponse<InstancesResponse>>

    @GET("/servers/{id}")
    suspend fun getInstance(
        @Path("id") instanceId : String
    ) : NetworkResult<InstanceData>

    @DELETE("/servers/{id}")
    suspend fun deleteInstance(
        @Path("id") instanceId: String
    ): NetworkResult<OpenstackResponse<String>>

}
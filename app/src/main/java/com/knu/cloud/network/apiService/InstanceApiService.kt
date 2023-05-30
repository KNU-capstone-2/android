package com.knu.cloud.network.apiService

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.SignUpRequest
import com.knu.cloud.model.auth.Token
import com.knu.cloud.model.home.instance.InstanceControlResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.home.instance.InstancesResponse
import retrofit2.http.*

interface InstanceApiService {

    @GET("/instances")
    suspend fun getAllInstances() : NetworkResult<AuthResponse<List<InstanceData>>>

    @GET("/instance/{id}")
    suspend fun getInstance(
        @Path("id") id : String
    ) : NetworkResult<AuthResponse<InstanceData>>

    @DELETE("/instance/{id}")
    suspend fun deleteInstance(
        @Path("id") id: String
    ): NetworkResult<AuthResponse<String>>

    @POST("/instance/start/{id}")
    suspend fun startInstance(
        @Path("id") id: String
    ): NetworkResult<AuthResponse<InstanceControlResponse>>


    @POST("/instance/reboot/{id}")
    suspend fun reStartInstance(
        @Path("id") id: String
    ): NetworkResult<AuthResponse<InstanceControlResponse>>


    @POST("/instance/stop/{id}")
    suspend fun stopInstance(
        @Path("id") id: String
    ): NetworkResult<AuthResponse<InstanceControlResponse>>
}
package com.knu.cloud.network.apiService

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

    @GET("/instances")
    suspend fun getAllInstances() : NetworkResult<List<InstanceData>>

    @GET("/instance/{id}")
    suspend fun getInstance(
        @Path("id") instanceId : String
    ) : NetworkResult<InstanceData>

    @DELETE("/instance/{id}")
    suspend fun deleteInstance(
        @Path("id") instanceId: String
    ): NetworkResult<String>

    @POST("/instance/start/{id}")
    suspend fun startInstance(
        @Path("id") instanceId: String
    ): NetworkResult<OpenstackResponse<String>>


    @POST("/instance/reboot/{id}")
    suspend fun reStartInstance(
        @Path("id") instanceId: String
    ): NetworkResult<OpenstackResponse<String>>


    @POST("/instance/stop/{id}")
    suspend fun stopInstance(
        @Path("id") instanceId: String
    ): NetworkResult<OpenstackResponse<String>>

}
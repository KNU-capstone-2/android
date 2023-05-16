package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.SignUpRequest
import com.knu.cloud.model.auth.Token
import com.knu.cloud.model.instance.InstanceData
import retrofit2.http.*

interface InstanceApiService {

    @GET("/instance/instances")
    suspend fun getAllInstances() : NetworkResult<AuthResponse<List<InstanceData>>>

    @GET("/instance/instance")
    suspend fun getInstance(
        @Query("id") instanceId : String
    ) : NetworkResult<AuthResponse<InstanceData>>

}
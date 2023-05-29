package com.knu.cloud.data.home.instance

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.home.instance.InstanceControlResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.network.apiService.InstanceApiService
import timber.log.Timber
import javax.inject.Inject

class InstanceRemoteDataSource @Inject constructor (
    private val instanceApiService : InstanceApiService
) {
    suspend fun getAllInstances(): NetworkResult<AuthResponse<List<InstanceData>>> {
        Timber.tag("network").d("InstanceRemoteDataSource getAllInstances() 호출")
        return instanceApiService.getAllInstances()
    }
    suspend fun getInstance(instanceId: String) : NetworkResult<AuthResponse<InstanceData>> {
        Timber.tag("network").d("InstanceRemoteDataSource getInstance($instanceId) 호출")
        return instanceApiService.getInstance(instanceId)
    }

    suspend fun deleteInstance(instanceId: String): NetworkResult<AuthResponse<String>> {
        Timber.tag("network").d("InstanceRemoteDataSource deleteInstance($instanceId) 호출")
        return instanceApiService.deleteInstance(instanceId)
    }
    suspend fun startInstance(instanceId: String): NetworkResult<AuthResponse<InstanceControlResponse>> {
        Timber.tag("network").d("InstanceRemoteDataSource startInstance($instanceId) 호출")
        return instanceApiService.startInstance(instanceId)
    }
    suspend fun reStartInstance(instanceId: String): NetworkResult<AuthResponse<InstanceControlResponse>> {
        Timber.tag("network").d("InstanceRemoteDataSource reStartInstance($instanceId) 호출")
        return instanceApiService.reStartInstance(instanceId)
    }
    suspend fun stopInstance(instanceId: String): NetworkResult<AuthResponse<InstanceControlResponse>> {
        Timber.tag("network").d("InstanceRemoteDataSource stopInstance($instanceId) 호출")
        return instanceApiService.stopInstance(instanceId)
    }
}
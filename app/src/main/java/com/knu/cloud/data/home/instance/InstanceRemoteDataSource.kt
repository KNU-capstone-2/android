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
    suspend fun getInstance(id: String) : NetworkResult<AuthResponse<InstanceData>> {
        Timber.tag("network").d("InstanceRemoteDataSource getInstance($id) 호출")
        return instanceApiService.getInstance(id)
    }

    suspend fun deleteInstance(id: String): NetworkResult<AuthResponse<String>> {
        Timber.tag("network").d("InstanceRemoteDataSource deleteInstance($id) 호출")
        return instanceApiService.deleteInstance(id)
    }
    suspend fun startInstance(id: String): NetworkResult<AuthResponse<InstanceControlResponse>> {
        Timber.tag("network").d("InstanceRemoteDataSource startInstance($id) 호출")
        return instanceApiService.startInstance(id)
    }
    suspend fun reStartInstance(id: String): NetworkResult<AuthResponse<InstanceControlResponse>> {
        Timber.tag("network").d("InstanceRemoteDataSource reStartInstance($id) 호출")
        return instanceApiService.reStartInstance(id)
    }
    suspend fun stopInstance(id: String): NetworkResult<AuthResponse<InstanceControlResponse>> {
        Timber.tag("network").d("InstanceRemoteDataSource stopInstance($id) 호출")
        return instanceApiService.stopInstance(id)
    }
}
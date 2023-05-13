package com.knu.cloud.repository

import com.knu.cloud.data.instance.InstanceRemoteDataSource
import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.network.authResponseToResult
import javax.inject.Inject

class InstanceRepositoryImpl @Inject constructor(
    private val remoteDataSource: InstanceRemoteDataSource
){
    /**
     * NetworkResult를 받아와서 Result로 Mapping해준다
     * Result로 처리해주는 이유는 viewModel에서 Success/Failure 처리 용이하도록 하기 위함
     */
    suspend fun getAllInstances() : Result<List<InstanceData>?>
        = authResponseToResult(remoteDataSource.getAllInstances())

    suspend fun getInstance(instanceId : String) : Result<InstanceData?>
        = authResponseToResult(remoteDataSource.getInstance(instanceId))

}
package com.knu.cloud.repository.home.instance

import com.knu.cloud.data.home.instance.InstanceRemoteDataSource
import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.home.instance.InstancesResponse
import com.knu.cloud.model.onError
import com.knu.cloud.model.onException
import com.knu.cloud.model.onSuccess
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.network.openstackResponseToResult
import com.knu.cloud.network.responseToResult
import javax.inject.Inject

class InstanceRepositoryImpl @Inject constructor(
    private val remoteDataSource: InstanceRemoteDataSource
): InstanceRepository {
    /**
     * NetworkResult를 받아와서 Result로 Mapping해준다
     * Result로 처리해주는 이유는 viewModel에서 Success/Failure 처리 용이하도록 하기 위함
     */
    override suspend fun getAllInstances(): Result<InstancesResponse?> =
        openstackResponseToResult(remoteDataSource.getAllInstances())

    override suspend fun getInstance(instanceId: String): Result<InstanceData?> =
        responseToResult(remoteDataSource.getInstance(instanceId))

    override suspend fun deleteInstance(instanceId: String): Result<String?> =
        openstackResponseToResult(remoteDataSource.deleteInstance(instanceId))
}
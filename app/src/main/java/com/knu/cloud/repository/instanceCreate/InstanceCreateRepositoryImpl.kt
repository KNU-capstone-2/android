package com.knu.cloud.repository.instanceCreate

import com.knu.cloud.data.instanceCreate.InstanceCreateDataSource
import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import com.knu.cloud.utils.instanceCreateResponseToResult
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstanceCreateRepositoryImpl @Inject constructor(
    private val remoteDataSource: InstanceCreateDataSource
): InstanceCreateRepository {

    override suspend fun createInstance(createRequest: CreateRequest): Result<InstanceData?> {
        val instanceCreateResponse = remoteDataSource.createInstance(createRequest)
        return instanceCreateResponseToResult(instanceCreateResponse)
    }

    override suspend fun getAllFlavorData(): Result<List<FlavorResponse>?> {
        val flavorResponse = remoteDataSource.getFlavorData()
        return instanceCreateResponseToResult(flavorResponse)
    }

    override suspend fun getAllKeypairData(): Result<List<KeypairResponse>?> {
        val keypairResponse = remoteDataSource.getKeypairData()
        return instanceCreateResponseToResult(keypairResponse)
    }

    override suspend fun getAllNetworkData(): Result<List<NetworkResponse>?> {
        val networkResponse = remoteDataSource.getNetworkData()
        return instanceCreateResponseToResult(networkResponse)
    }

    override suspend fun getAllSourceData(): Result<List<SourceResponse>?> {
        val sourceResponse = remoteDataSource.getSourceData()
        return instanceCreateResponseToResult(sourceResponse)
    }

}
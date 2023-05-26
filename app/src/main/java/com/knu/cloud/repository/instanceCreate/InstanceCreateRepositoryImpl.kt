package com.knu.cloud.repository.instanceCreate

import com.knu.cloud.data.instanceCreate.InstanceCreateRemoteDataSource
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import com.knu.cloud.network.openstackResponseToResult
import com.knu.cloud.utils.instanceCreateResponseToResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstanceCreateRepositoryImpl @Inject constructor(
    private val remoteDataSource: InstanceCreateRemoteDataSource
): InstanceCreateRepository {

    override suspend fun createInstance(createRequest: CreateRequest): Result<InstanceData?> {
        val instanceCreateResponse = remoteDataSource.createInstance(createRequest)
        return instanceCreateResponseToResult(instanceCreateResponse)
    }

    override suspend fun getAllFlavorData(): Result<FlavorsResponse?> {
        val flavorResponse = remoteDataSource.getFlavorData()
        return openstackResponseToResult(flavorResponse)
    }

    override suspend fun getAllKeypairData(): Result<KeypairsResponse?> {
        val keypairResponse = remoteDataSource.getKeypairData()
        return openstackResponseToResult(keypairResponse)
    }

    override suspend fun getAllNetworkData(): Result<NetworksResponse?> {
        val networkResponse = remoteDataSource.getNetworkData()
        return openstackResponseToResult(networkResponse)
    }

    override suspend fun getAllSourceData(): Result<SourcesResponse?> {
        val sourceResponse = remoteDataSource.getSourceData()
        return openstackResponseToResult(sourceResponse)
    }

}
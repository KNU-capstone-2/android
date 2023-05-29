package com.knu.cloud.repository.instanceCreate

import com.knu.cloud.data.instanceCreate.InstanceCreateRemoteDataSource
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.instanceCreate.*
import com.knu.cloud.network.authResponseToResult
import com.knu.cloud.network.openstackResponseToResult
import com.knu.cloud.network.responseToResult
import com.knu.cloud.utils.instanceCreateResponseToResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstanceCreateRepositoryImpl @Inject constructor(
    private val remoteDataSource: InstanceCreateRemoteDataSource
): InstanceCreateRepository {

    override suspend fun createInstance(createRequest: CreateRequest): Result<InstanceData?> {
        val instanceCreateResponse = remoteDataSource.createInstance(createRequest)
        return authResponseToResult(instanceCreateResponse)
    }

    override suspend fun getAllFlavorData(): Result<List<FlavorData>?> {
        val flavorResponse = remoteDataSource.getFlavorData()
        return authResponseToResult(flavorResponse)
    }

    override suspend fun getAllKeypairData(): Result<List<KeypairData>?> {
        val keypairResponse = remoteDataSource.getKeypairData()
        return authResponseToResult(keypairResponse)
    }

    override suspend fun getAllNetworkData(): Result<List<NetworkData>?> {
        val networkResponse = remoteDataSource.getNetworkData()
        return authResponseToResult(networkResponse)
    }

    override suspend fun getAllSourceData(): Result<List<ImageData>?> {
        val sourceResponse = remoteDataSource.getImages()
        return authResponseToResult(sourceResponse)
    }

}
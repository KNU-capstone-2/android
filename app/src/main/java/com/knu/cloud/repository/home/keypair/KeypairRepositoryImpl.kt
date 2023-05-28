package com.knu.cloud.repository.home.keypair

import com.knu.cloud.data.home.keypair.KeypairRemoteDataSource
import com.knu.cloud.model.instanceCreate.KeypairData
import com.knu.cloud.model.instanceCreate.KeypairsResponse
import com.knu.cloud.model.keypair.KeypairCreateRequest
import com.knu.cloud.model.keypair.KeypairCreateResponse
import com.knu.cloud.network.openstackResponseToResult
import com.knu.cloud.network.responseToResult
import javax.inject.Inject

class KeypairRepositoryImpl @Inject constructor(
    private val remoteDataSource: KeypairRemoteDataSource
) : KeypairRepository{
    override suspend fun getKeypairs(): Result<List<KeypairData>?> {
        return responseToResult(remoteDataSource.getKeypairs())
    }

    override suspend fun getKeypair(keypairName: String): Result<KeypairData?> {
        return responseToResult(remoteDataSource.getKeypair(keypairName))
    }

    override suspend fun createKeypair(keypairCreateRequest: KeypairCreateRequest): Result<KeypairCreateResponse?> {
        return responseToResult(remoteDataSource.createKeypair(keypairCreateRequest))
    }

    override suspend fun deleteKeypair(keypairName: String): Result<String?> {
        return responseToResult(remoteDataSource.deleteKeypair(keypairName))
    }
}
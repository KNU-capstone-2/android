package com.knu.cloud.data.home.keypair

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.instanceCreate.ImagesResponse
import com.knu.cloud.model.instanceCreate.KeypairData
import com.knu.cloud.model.instanceCreate.KeypairsResponse
import com.knu.cloud.model.keypair.KeypairCreateRequest
import com.knu.cloud.model.keypair.KeypairCreateResponse
import com.knu.cloud.network.ImageApiService
import com.knu.cloud.network.KeypairApiService
import javax.inject.Inject

class KeypairRemoteDataSource @Inject constructor(
    private val keypairApiService: KeypairApiService
) {

    suspend fun getKeypairs() : NetworkResult<OpenstackResponse<KeypairsResponse>> {
        return keypairApiService.getKeypairs()
    }

    suspend fun getKeypair(keypairName: String) : NetworkResult<KeypairData>{
        return keypairApiService.getKeypair(keypairName)
    }

    suspend fun createKeypair(keypairCreateRequest: KeypairCreateRequest) : NetworkResult<KeypairCreateResponse>{
        return keypairApiService.createKeypair(keypairCreateRequest)
    }

    suspend fun deleteKeypair(keypairName: String): NetworkResult<OpenstackResponse<String>> {
        return keypairApiService.deleteKeypair(keypairName)
    }

}
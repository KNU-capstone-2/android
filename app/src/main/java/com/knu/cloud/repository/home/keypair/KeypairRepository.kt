package com.knu.cloud.repository.home.keypair

import com.knu.cloud.model.instanceCreate.KeypairData
import com.knu.cloud.model.instanceCreate.KeypairsResponse
import com.knu.cloud.model.keypair.KeypairCreateRequest
import com.knu.cloud.model.keypair.KeypairCreateResponse

interface KeypairRepository {

    suspend fun getKeypairs() : Result<List<KeypairData>?>

    suspend fun getKeypair(keypairName : String) : Result<KeypairData?>

    suspend fun createKeypair(keypairCreateRequest: KeypairCreateRequest) : Result<KeypairCreateResponse?>

    suspend fun deleteKeypair(keypairName: String) : Result<String?>

}
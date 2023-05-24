package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import timber.log.Timber

class RetrofitFailureStateException(error: String ?, val code: Int) : Exception(error)

fun <T:Any> authResponseToResult(networkResult: NetworkResult<AuthResponse<T>>) :Result<T?>{
    Timber.tag("network").d("AuthResponse ${networkResult.toString()}")
    return when(networkResult){
        is NetworkResult.Success  -> {
            Result.success(networkResult.data.data)
        }
        is NetworkResult.Error -> Result.failure(
            RetrofitFailureStateException(
                networkResult.message,
                networkResult.code
            )
        )
        is NetworkResult.Exception -> Result.failure(networkResult.e)

    }
}

fun <T:Any> openstackResponseToResult(networkResult: NetworkResult<OpenstackResponse<T>>): Result<T?> {
    return when(networkResult) {
        is NetworkResult.Success -> {
            Result.success(networkResult.data.data)
        }
        is NetworkResult.Error -> Result.failure(
            RetrofitFailureStateException(
                networkResult.message,
                networkResult.code
            )
        )
        is NetworkResult.Exception -> Result.failure(networkResult.e)
    }
}

fun <T:Any> responseToResult(networkResult: NetworkResult<T>): Result<T?> {
    return when(networkResult) {
        is NetworkResult.Success -> {
            Result.success(networkResult.data)
        }
        is NetworkResult.Error -> Result.failure(
            RetrofitFailureStateException(
                networkResult.message,
                networkResult.code
            )
        )
        is NetworkResult.Exception -> Result.failure(networkResult.e)
    }
}
package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.home.instance.InstanceControlResponse
import timber.log.Timber

class RetrofitFailureStateException(error: String ?, val code: Int = 999) : Exception(error)

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
            Timber.tag("network").d("OpenstackResponse Success : ${networkResult.data.data}")
            Result.success(networkResult.data.data)
        }
        is NetworkResult.Error -> {
            Timber.tag("network").d("OpenstackResponse Error message : ${networkResult.message} code : ${networkResult.code}")
                Result.failure(
                    RetrofitFailureStateException(
                        networkResult.message,
                        networkResult.code
                    )
                )
            }
        is NetworkResult.Exception -> {
            Timber.tag("network").d("OpenstackResponse Exception e : ${networkResult.e}")
            Result.failure(
                RetrofitFailureStateException(
                    networkResult.e.message
                )
            )
        }
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
        is NetworkResult.Exception -> Result.failure(
            RetrofitFailureStateException(
                networkResult.e.message
            )
        )
    }
}
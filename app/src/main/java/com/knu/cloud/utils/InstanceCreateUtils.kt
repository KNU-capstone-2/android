package com.knu.cloud.utils

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.AuthResponse
import retrofit2.Response

fun <T:Any> instanceCreateResponseToResult(networkResult: NetworkResult<AuthResponse<T>>): Result<T?> {
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
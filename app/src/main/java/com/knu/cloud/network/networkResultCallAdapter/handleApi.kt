package com.knu.cloud.network.networkResultCallAdapter

import com.knu.cloud.model.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

fun <T : Any> handleApi(
    execute: () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Timber.tag("network").d("handleApi : response.isSuccessful && body not null")
            NetworkResult.Success(body)
        } else {
            Timber.tag("network").d("handleApi : response not Successful or body is null ")
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        Timber.tag("network").e("handleApi : Error HttpException code : ${e.code()} message : ${e.message}")
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        Timber.tag("network").e("handleApi : Exception message :  ${e.message}")
        NetworkResult.Exception(e)
    }
}
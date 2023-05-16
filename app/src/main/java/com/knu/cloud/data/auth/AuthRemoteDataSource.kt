package com.knu.cloud.data.auth

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.*
import com.knu.cloud.network.AuthApiService
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor (
    private val authApiService : AuthApiService
) {
    suspend fun login(loginRequest: LoginRequest): NetworkResult<AuthResponse<Token>> {
        Timber.tag("network").d("AuthRemoteDataSource login fun 호출")
        return authApiService.login(loginRequest)
    }
    suspend fun signUp(signUpRequest: SignUpRequest) : NetworkResult<AuthResponse<Token>> {
        Timber.tag("network").d("AuthRemoteDataSource signUp fun 호출")
        return authApiService.signUp(signUpRequest)
    }
}
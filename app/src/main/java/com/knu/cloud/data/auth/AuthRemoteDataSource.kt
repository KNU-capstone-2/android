package com.knu.cloud.data.auth

import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.Token
import com.knu.cloud.network.AuthApiService
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor (
    private val authApiService : AuthApiService
) {

    suspend fun getToken(loginRequest: LoginRequest): Token {
        // TODO: 여기에서 Response처리해주자

//        return authApiService.login(loginRequest)
        return Token("")
    }
}
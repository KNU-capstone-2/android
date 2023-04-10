package com.knu.cloud.network

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {

//    @Headers(
//        "Content-Type:application/json",
//        "Authorization:Bearer API")
    @POST("/user/sign-up")
    suspend fun signUp(
        @Body signUpRequest : SignUpRequest
    ) : NetworkResult<AuthResponse<Token>>

    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : NetworkResult<AuthResponse<Token>>
}
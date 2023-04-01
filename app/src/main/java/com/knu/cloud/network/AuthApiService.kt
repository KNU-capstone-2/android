package com.knu.cloud.network

import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.SignUpRequest
import com.knu.cloud.model.auth.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {

//    @Headers(
//        "Content-Type:application/json",
//        "Authorization:Bearer API")
    @POST("/auth/signup")
    suspend fun signUp(
        @Body signUpRequest : SignUpRequest
    ) : Call<AuthResponse<Token>>

    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : Call<AuthResponse<Token>>
}
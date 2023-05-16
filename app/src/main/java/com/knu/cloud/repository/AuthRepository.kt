package com.knu.cloud.repository

import com.knu.cloud.data.auth.AuthRemoteDataSource
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    suspend fun signUp(email :String , username : String, password:String): Result<String>
    suspend fun login(id : String, password : String): Result<String>

}

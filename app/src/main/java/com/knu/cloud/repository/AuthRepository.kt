package com.knu.cloud.repository

import com.knu.cloud.model.auth.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val isSignIn: StateFlow<Boolean>
    val signInError: StateFlow<String>

    fun getTokenFlow() : Flow<Token>
    suspend fun saveToken(token: Token)

    fun login(userEmail: String, userPassword: String)

}

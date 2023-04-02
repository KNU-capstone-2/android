package com.knu.cloud.repository

import com.knu.cloud.model.auth.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getTokenFlow() : Flow<Token>
    suspend fun saveToken(token: Token)

}

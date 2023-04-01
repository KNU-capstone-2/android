package com.knu.cloud.repository

import com.knu.cloud.model.auth.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository {
    suspend fun getTokenFlow() : Flow<Token>{
        // TODO : TokenFlow 제공하는 함수 구현 -> AuthInterceptor에서 사용
        // DataStore에서 꺼내와서 주면 될거임
        return flow{ Token() }
    }

    suspend fun saveToken(token: Token){
        // TODO: RemoteAuthDataSource에서 토큰 받아와서 LocalAuthDataSource의 saveToken() 수행 (자동 로그인을 위함)
    }
}

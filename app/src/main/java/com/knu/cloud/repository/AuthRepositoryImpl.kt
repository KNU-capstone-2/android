package com.knu.cloud.repository

import com.knu.cloud.data.auth.AuthLocalDataSource
import com.knu.cloud.data.auth.AuthRemoteDataSource
import com.knu.cloud.model.auth.Token
import com.knu.cloud.network.AuthApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteDataSource,
    private val local: AuthLocalDataSource
): AuthRepository {
    override val isSignIn = MutableStateFlow<Boolean>(false)
    override val signInError = MutableStateFlow<String>("")

    override fun getTokenFlow() : Flow<Token>{
        // TODO : TokenFlow 제공하는 함수 구현 -> AuthInterceptor에서 사용
        // DataStore에서 꺼내와서 주면 될거임
        return flow{ Token() }
    }

    override suspend fun saveToken(token: Token){
        // TODO: RemoteAuthDataSource에서 토큰 받아와서 LocalAuthDataSource의 saveToken() 수행 (자동 로그인을 위함)
    }

    override fun login(userEmail: String, userPassword: String) {
        // remote.login(email = userEmail, password = userPassword)
    }

}

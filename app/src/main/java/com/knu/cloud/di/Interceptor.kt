package com.knu.cloud.di

import com.knu.cloud.model.auth.Token
import com.knu.cloud.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Interceptor {

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        @Named("tokenFlow") tokenFlow: Flow<Token>
    ): AuthInterceptor {
        return AuthInterceptor(tokenFlow)
    }

    // TODO: TokenFlow가 여기 들ㅇ가는게 맞는지?
    @Singleton
    @Provides
    suspend fun provideTokenFlow(authRepository: AuthRepositoryImpl): Flow<Token> {
        return authRepository.getTokenFlow()
            .flowOn(Dispatchers.IO)
    }

}

// 헤더에 Token 추가하는 Interceptor
class AuthInterceptor(
    private val tokenFlow : Flow<Token>
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val authToken = runBlocking { tokenFlow.first()  }
        val newRequest = request().newBuilder()
            .addHeader("Authorization", "Bearer ${authToken.sessionId}")
            .build()
        proceed(newRequest)
    }
}


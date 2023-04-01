package com.knu.cloud.di

import com.knu.cloud.model.auth.Token
import com.knu.cloud.network.AuthApiService
import com.knu.cloud.repository.AuthRepository
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
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("BASE_URL")
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        @Named("tokenFlow") tokenFlow: Flow<Token>
    ):AuthInterceptor{
        return AuthInterceptor(tokenFlow)
    }

    @Singleton
    @Provides
    suspend fun provideTokenFlow(authRepository: AuthRepository): Flow<Token> {
        return authRepository.getTokenFlow()
            .flowOn(Dispatchers.IO)
    }

    fun provideAuthApiService(retrofit: Retrofit): AuthApiService
        = retrofit.create(AuthApiService::class.java)
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
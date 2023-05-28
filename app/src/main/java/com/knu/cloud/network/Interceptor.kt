package com.knu.cloud.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object Interceptor {

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        sessionManager: SessionManager
    ): AuthInterceptor {
        return AuthInterceptor(sessionManager)
    }
}

// 헤더에 Token 추가하는 Interceptor
class AuthInterceptor(
    private val sessionManager: SessionManager
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionId = runBlocking { sessionManager.sessionId.value }
        val newRequest = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer $sessionId")
            .build()

        val response = chain.proceed(newRequest)
        if(response.header("Authorization") != null){
            // token이 vaild할 경우 sessionId를 저장해줘야함
            sessionManager.setSessionId(response.header("Authorization")!!)
            Timber.tag("login").d("sessionId : ${response.header("Authorization")!!}")
        }
        // token이 invalid 하면  이미 response 객체에 오류가 붙어서 올거임-> NetworkCallAdapter가 그 뒤에 처리할거임
        return response
    }
}
package com.knu.cloud.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
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
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val authState = sessionManager.authState.value
        val authRequestBuilder = chain.request().newBuilder()
        if(authState.isLoggedIn){
            val sessionId = runBlocking {
                withTimeoutOrNull(2000)    {
                    sessionManager.authState.value.sessionId
                }
            }
            Timber.d("request sessionId : $sessionId")
            authRequestBuilder.addHeader("Cookie", "$sessionId")
        }
        val authRequest = authRequestBuilder.build()

        val authResponse = chain.proceed(authRequest)
        val newSessionId = parseSessionId(authResponse.header("set-cookie")?: "")
        Timber.d("newSessionId : $newSessionId")
        if(newSessionId != null){
            Timber.d("isLoggedIn : ${authState.isLoggedIn}")
            if(authState.isLoggedIn) {
                Timber.d("setSessionID")
//                sessionManager.setSessionId(newSessionId)
            }else {
                Timber.d("login")
                sessionManager.login(newSessionId)
            }
        }
//        else sessionManager.setSessionId("")
        // token이 invalid 하면  이미 response 객체에 오류가 붙어서 올거임-> NetworkCallAdapter가 그 뒤에 처리할거임
        return authResponse
    }
}

fun parseSessionId(input: String): String? {
    return input.split(";")[0]
//    val pattern = Regex("JSESSIONID=([a-zA-Z0-9]+);")
//    val matchResult = pattern.find(input)
//    return matchResult?.groupValues?.get(1)
}
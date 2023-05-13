package com.knu.cloud.mockWebServer

import com.knu.cloud.di.AuthInterceptor
import com.knu.cloud.di.SessionManager
import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.onSuccess
import com.knu.cloud.network.AuthApiService
import com.knu.cloud.network.InstanceApiService
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.network.authResponseToResult
import com.knu.cloud.network.networkResultCallAdapter.NetworkResultCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class InstanceApiServiceTest {

    private lateinit var service: InstanceApiService
    private lateinit var server: MockWebServer

    private val sessionManager = SessionManager()

    @Before
    fun setup(){
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .client(
                OkHttpClient.Builder()
                .addNetworkInterceptor(AuthInterceptor(sessionManager))
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
            .create(InstanceApiService::class.java)
        val baseUrl : HttpUrl = server.url("/")
    }

    private fun setResponseBody(
        mockResponse : MockResponse,
        fileName : String
    ): MockResponse {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        return mockResponse
    }

    private var checkStatus : Int = 0
    @Test
    fun `getAllInstances success 200`(){
        runBlocking {
            var mockResponse = MockResponse()
            mockResponse = setResponseBody(mockResponse, fileName = "instances.json")
            mockResponse.addHeader("Authorization","token1234")
            server.enqueue(mockResponse)

            val response = service.getAllInstances()
            val request = server.takeRequest()

            // sessionManager에 sessionId 들어왔는지 확인하기
            Assert.assertEquals("token1234",sessionManager.sessionId.value)

            // networkResultCallAdapter를 통해 NetworkResult로 잘 변환되었는지 확인하기
            response.onSuccess { authResponse ->
                Timber.tag("getAllInstances success 200").d("authResponse : ${authResponse}")
                checkStatus = authResponse.status
            }

            Assert.assertEquals(200,checkStatus)

            // authRepository의 login함수 처럼 써보기
            val result = authResponseToResult(response)

            result.onSuccess {
                Assert.assertNotNull(it)
            }
        }
    }
}
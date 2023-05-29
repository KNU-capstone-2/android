package com.knu.cloud.di

import com.knu.cloud.network.AuthInterceptor
import com.knu.cloud.network.SessionManager
import com.knu.cloud.network.networkResultCallAdapter.NetworkResultCallAdapterFactory
import com.knu.cloud.network.nullOrEmptyConverter.NullOrEmptyConverterFactory
import com.knu.cloud.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

//    private const val BASE_URL = "https://d8f3-39-116-133-230.ngrok-free.app"
    private const val BASE_URL = Constants.SPRING_BASE_URL
//    private const val BASE_URL = "https://a923-211-51-176-234.ngrok-free.app"
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(NullOrEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            this.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
//            .connectTimeout(100, TimeUnit.MILLISECONDS)
//            .readTimeout(100, TimeUnit.MILLISECONDS)
//            .writeTimeout(100, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .addNetworkInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideSessionManager() : SessionManager {
        return SessionManager()
    }
}



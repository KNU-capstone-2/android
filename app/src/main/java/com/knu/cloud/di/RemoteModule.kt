package com.knu.cloud.di

import com.knu.cloud.network.AuthApiService
import com.knu.cloud.network.DashboardApiService
import com.knu.cloud.network.InstanceCreateApiService
import com.knu.cloud.network.InstanceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideInstanceCreateApiService(retrofit: Retrofit): InstanceCreateApiService {
        return retrofit.create(InstanceCreateApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideInstanceApiService(retrofit: Retrofit): InstanceApiService {
        return retrofit.create(InstanceApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardApiService(retrofit: Retrofit): DashboardApiService {
        return retrofit.create(DashboardApiService::class.java)
    }

}
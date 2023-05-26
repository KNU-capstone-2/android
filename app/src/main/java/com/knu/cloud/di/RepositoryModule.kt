package com.knu.cloud.di

import com.knu.cloud.repository.AuthRepository
import com.knu.cloud.repository.AuthRepositoryImpl
import com.knu.cloud.repository.OpenStackRepository
import com.knu.cloud.repository.OpenStackRepositoryImpl
import com.knu.cloud.repository.home.dashboard.DashboardRepository
import com.knu.cloud.repository.home.dashboard.DashboardRepositoryImpl
import com.knu.cloud.repository.home.instance.InstanceRepository
import com.knu.cloud.repository.home.instance.InstanceRepositoryImpl
import com.knu.cloud.repository.instanceCreate.InstanceCreateRepository
import com.knu.cloud.repository.instanceCreate.InstanceCreateRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
    interface인 Repository를 주입하기 위한
    Repository Module 작성
*/
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository (
        AuthRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun bindOpenStackRepository (
        OpenStackRepositoryImpl: OpenStackRepositoryImpl
    ): OpenStackRepository

    @Singleton
    @Binds
    abstract fun bindInstanceCreateRepository (
        InstanceCreateRepositoryImpl: InstanceCreateRepositoryImpl
    ): InstanceCreateRepository

    @Singleton
    @Binds
    abstract fun bindInstanceRepository (
        InstanceRepositoryImpl: InstanceRepositoryImpl
    ): InstanceRepository

    @Singleton
    @Binds
    abstract fun bindDashboardRepository (
        DashboardRepositoryImpl: DashboardRepositoryImpl
    ): DashboardRepository
}
package com.knu.cloud.repository.home.dashboard

import com.knu.cloud.data.home.Dashboard.DashboardRemoteDataSource
import com.knu.cloud.model.home.dashboard.DashboardResponse
import com.knu.cloud.model.home.instance.InstancesResponse
import com.knu.cloud.network.openstackResponseToResult
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val remoteDataSource: DashboardRemoteDataSource
): DashboardRepository {

    override suspend fun getDashboardData(): Result<DashboardResponse?> =
        openstackResponseToResult(remoteDataSource.getDashboardData())

}
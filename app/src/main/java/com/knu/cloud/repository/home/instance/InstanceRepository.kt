package com.knu.cloud.repository.home.instance

import com.knu.cloud.model.home.instance.InstanceControlResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.home.instance.InstancesResponse
import com.knu.cloud.model.instanceCreate.*

interface InstanceRepository {

    suspend fun getAllInstances() : Result<List<InstanceData>?>

    suspend fun getInstance(id : String) : Result<InstanceData?>

    suspend fun deleteInstance(id: String) : Result<String?>

    suspend fun startInstance(id: String) : Result<InstanceControlResponse?>
    suspend fun reStartInstance(id: String) : Result<InstanceControlResponse?>
    suspend fun stopInstance(id: String) : Result<InstanceControlResponse?>

}
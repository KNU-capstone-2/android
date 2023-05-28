package com.knu.cloud.repository.home.instance

import com.knu.cloud.model.home.instance.InstanceControlResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.home.instance.InstancesResponse
import com.knu.cloud.model.instanceCreate.*

interface InstanceRepository {

    suspend fun getAllInstances() : Result<List<InstanceData>?>

    suspend fun getInstance(instanceId : String) : Result<InstanceData?>

    suspend fun deleteInstance(instanceId: String) : Result<String?>

    suspend fun startInstance(instanceId: String) : Result<InstanceControlResponse?>
    suspend fun reStartInstance(instanceId: String) : Result<InstanceControlResponse?>
    suspend fun stopInstance(instanceId: String) : Result<InstanceControlResponse?>

}
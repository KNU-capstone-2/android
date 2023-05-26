package com.knu.cloud.repository.home.instance

import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.home.instance.InstancesResponse
import com.knu.cloud.model.instanceCreate.*

interface InstanceRepository {

    suspend fun getAllInstances() : Result<InstancesResponse?>

    suspend fun getInstance(instanceId : String) : Result<InstanceData?>

    suspend fun deleteInstance(instanceId: String) : Result<String?>

}
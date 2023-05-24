package com.knu.cloud.repository.instance

import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.model.instance.InstancesResponse
import com.knu.cloud.model.instanceCreate.*

interface InstanceRepository {

    suspend fun getAllInstances() : Result<InstancesResponse?>

    suspend fun getInstance(instanceId : String) : Result<InstanceData?>

    suspend fun deleteInstance(instanceId: String) : Result<String?>

}
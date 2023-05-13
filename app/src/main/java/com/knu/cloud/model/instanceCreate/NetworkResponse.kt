package com.knu.cloud.model.instanceCreate

data class NetworkResponse(
    val network: String,
    val subNet: String,
    val share: String,
    val adminState: String,
    val state: String
)
package com.knu.cloud.model.instanceCreate

data class Network(
    val network: String,
    val subNet: String,
    val share: String,
    val adminState: String,
    val state: String
)
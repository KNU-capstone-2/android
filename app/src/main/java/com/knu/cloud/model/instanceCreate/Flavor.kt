package com.knu.cloud.model.instanceCreate

data class Flavor (
    val name: String,
    val vcpus: Int,
    val ram: Int,
    val diskTotal: Int,
    val rootDisk: Int,
    val ephemeralDisk: Int,
    val public: String
)
package com.knu.cloud.model.instanceCreate

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("updateDate") // 업데이트 완료
    val update: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("createdDate") // 만들어진 날
    val format: String,
    @SerializedName("status") // 상태
    val visible: String
)

data class SourcesResponse(
    @SerializedName("images")
    val images : List<SourceResponse>
)
package com.knu.cloud.model.instanceCreate

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updateDate") // 업데이트 완료
    val updateDate: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("createdDate") // 만들어진 날
    val createdDate: String,
    @SerializedName("status") // 상태
    val status: String
)

data class ImagesResponse(
    @SerializedName("images")
    val images : List<ImageData>
)
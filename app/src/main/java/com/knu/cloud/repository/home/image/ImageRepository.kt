package com.knu.cloud.repository.home.image

import com.knu.cloud.model.instanceCreate.ImagesResponse

interface ImageRepository {

    suspend fun getImages(): Result<ImagesResponse?>

    suspend fun deleteImage(imageId: String) : Result<String?>

}
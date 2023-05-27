package com.knu.cloud.data.home.image

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.instanceCreate.ImageData
import com.knu.cloud.model.instanceCreate.ImagesResponse
import com.knu.cloud.network.ImageApiService
import javax.inject.Inject


class ImageRemoteDataSource @Inject constructor(
    private val imageApiService: ImageApiService
) {

    suspend fun getImages() : NetworkResult<List<ImageData>> {
        return imageApiService.getImages()
    }

    suspend fun deleteImage(imageId: String): NetworkResult<String> {
        return imageApiService.deleteImage(imageId)
    }

}
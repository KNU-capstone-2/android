package com.knu.cloud.data.home.image

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.instanceCreate.ImageData
import com.knu.cloud.network.apiService.ImageApiService
import javax.inject.Inject


class ImageRemoteDataSource @Inject constructor(
    private val imageApiService: ImageApiService
) {

    suspend fun getImages() : NetworkResult<AuthResponse<List<ImageData>>> {
        return imageApiService.getImages()
    }

    suspend fun deleteImage(imageId: String): NetworkResult<AuthResponse<String>> {
        return imageApiService.deleteImage(imageId)
    }

}
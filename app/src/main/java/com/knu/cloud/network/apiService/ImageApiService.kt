package com.knu.cloud.network.apiService

import com.knu.cloud.model.NetworkResult
import com.knu.cloud.model.OpenstackResponse
import com.knu.cloud.model.auth.AuthResponse
import com.knu.cloud.model.instanceCreate.ImageData
import com.knu.cloud.model.instanceCreate.ImagesResponse
import retrofit2.http.DELETE
import retrofit2.http.GET

interface ImageApiService {

    @GET("/images")
    suspend fun getImages(): NetworkResult<AuthResponse<List<ImageData>>>

    @DELETE("/images/{id}")
    suspend fun deleteImage(imageId: String): NetworkResult<AuthResponse<String>>

}
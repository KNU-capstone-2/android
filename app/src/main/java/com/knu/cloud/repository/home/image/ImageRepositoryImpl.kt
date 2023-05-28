package com.knu.cloud.repository.home.image

import com.knu.cloud.data.home.image.ImageRemoteDataSource
import com.knu.cloud.data.instanceCreate.InstanceCreateRemoteDataSource
import com.knu.cloud.model.instanceCreate.ImageData
import com.knu.cloud.model.instanceCreate.ImagesResponse
import com.knu.cloud.network.openstackResponseToResult
import com.knu.cloud.network.responseToResult
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImageRemoteDataSource
): ImageRepository {

    override suspend fun getImages(): Result<List<ImageData>?> {
        return responseToResult(remoteDataSource.getImages())
    }

    override suspend fun deleteImage(imageId: String): Result<String?> {
        return responseToResult(remoteDataSource.deleteImage(imageId))
    }
}

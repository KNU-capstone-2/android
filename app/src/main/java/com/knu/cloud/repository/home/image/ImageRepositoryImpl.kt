package com.knu.cloud.repository.home.image

import com.knu.cloud.data.home.image.ImageRemoteDataSource
import com.knu.cloud.data.instanceCreate.InstanceCreateRemoteDataSource
import com.knu.cloud.model.instanceCreate.ImagesResponse
import com.knu.cloud.network.openstackResponseToResult
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImageRemoteDataSource
): ImageRepository {

    override suspend fun getImages(): Result<ImagesResponse?> {
        return openstackResponseToResult(remoteDataSource.getImages())
    }

    override suspend fun deleteImage(imageId: String): Result<String?> {
        return openstackResponseToResult(remoteDataSource.deleteImage(imageId))
    }
}

package com.knu.cloud.screens.home.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.instanceCreate.ImageData
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.repository.home.image.ImageRepository
import com.knu.cloud.utils.convertDateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class ImageUiState(
    val isLoading : Boolean = false,
    val images : List<ImageData> = emptyList(),
    val checkedImageIds : List<String> = emptyList(),
    val deleteComplete : Boolean = false,
    val deleteResult : List<Pair<String,Boolean>> = emptyList()
)

@HiltViewModel
class ImageViewModel @Inject constructor (
    private val imageRepository : ImageRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(ImageUiState())
    val uiState :StateFlow<ImageUiState> = _uiState.asStateFlow()
    init {
        getAllImages()
    }

    /**
     * Image를 전부 가져옴
     */
    private fun getAllImages(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            imageRepository.getImages()
                .onSuccess { images ->
                    if (images != null) {
                        Timber.d("images :  $images")
                        _uiState.update { it.copy(images = convertImageData(images), isLoading = false) }
                    }else{
                        _uiState.update { it.copy(images = emptyList(), isLoading = false) }
                    }
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(images = emptyList(), isLoading = false)
                    }
                    it as RetrofitFailureStateException
                    Timber.e("message :${it.message} , code :${it.code}")
                }
        }
    }
    /**
     * 체크된 image들을 삭제함
    * */
    fun deleteImages(){
        viewModelScope.launch {
            val deleteSuccessList : MutableList<String> = mutableListOf()
            _uiState.value.checkedImageIds.forEach{ imageId ->
                imageRepository.deleteImage(imageId)
                    .onSuccess {
                        deleteSuccessList.add(imageId)
                    }.onFailure {
                        it as RetrofitFailureStateException
                        Timber.e("message :${it.message} , code :${it.code}")
                    }
            }
            _uiState.update { state ->
                state.copy(
                    images = state.images.filterNot { it.id in deleteSuccessList },                              // 삭제 성공한  리스트에 없는 images
                    deleteResult = state.checkedImageIds.map { id ->
                        Pair(id, id in deleteSuccessList)
                    },
                    deleteComplete = true
                )
            }
        }
    }

    fun imageCheck(imageId : String) {
        if(imageId !in _uiState.value.checkedImageIds){
            _uiState.update { it.copy(checkedImageIds = it.checkedImageIds + imageId) }
        }
        Timber.d("checkedImageIds : ${uiState.value.checkedImageIds}")
    }
    fun imageUncheck(imageId: String) {
        _uiState.update { state ->
            state.copy(
                checkedImageIds = state.checkedImageIds.filterNot { it == imageId }
            )
        }
        Timber.d("checkedImageIds : ${uiState.value.checkedImageIds}")
    }

    fun allImagesCheck(allChecked: Boolean) {
        if(allChecked) {
            _uiState.update { state ->
                state.copy( checkedImageIds = state.images.map { it.id})
            }
        }else initializeCheckImageIds()
        Timber.d("checkedImageIds : ${uiState.value.checkedImageIds}")
    }

    fun closeDeleteResultDialog(){
        _uiState.update {
            it.copy( deleteComplete = false, checkedImageIds = emptyList())
        }
    }

    private fun initializeCheckImageIds(){
        _uiState.update { state ->
            state.copy(checkedImageIds = emptyList())
        }
        Timber.d("checkedImageIds : ${uiState.value.checkedImageIds}")
    }

    private fun convertImageData(images : List<ImageData>) :List<ImageData>{
        val convertedImages = mutableListOf<ImageData>()
        images.forEach { image ->
            convertedImages.add(image.copy(
                createdDate = convertDateFormat(image.createdDate),
                updateDate = convertDateFormat(image.updateDate))
            )
        }
        Timber.d("convertedImages :  $images")
        return convertedImages.toList()
    }

}
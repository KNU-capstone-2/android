package com.knu.cloud.screens.home.instanceDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.home.instance.InstanceControlResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.repository.home.instance.InstanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class InstanceDetailUiState(
    val isLoading : Boolean = false,
    val instance : InstanceData? = null,
    val message : String = "",
)

@HiltViewModel
class InstanceDetailViewModel @Inject constructor (
    private val instanceRepository: InstanceRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(InstanceDetailUiState())
    val uiState : StateFlow<InstanceDetailUiState> = _uiState.asStateFlow()

    fun getInstance(id : String){
        Timber.tag("${this.javaClass.name}_getInstance").d("getInstance($id)")
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch{
            instanceRepository.getInstance(id)
                .onSuccess { instanceData ->
                        Timber.tag("${this.javaClass.name}_getInstance").d("onSuccess instanceData : $instanceData")
                        _uiState.update { it.copy(instance = instanceData, isLoading = false) }
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(instance = null, isLoading = false)
                    }
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getInstance")
                        .e("onFailure message :${it.message} , code :${it.code}")
                }
        }
    }

    fun startInstance(id : String) {
        Timber.tag("startInstance").d("START")
        viewModelScope.launch {
            instanceRepository.startInstance(id)
                .onSuccess {
                    instanceControlSuccessHandling(it,"Start")
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(message = "network error")
                    }
                }
        }
    }

    fun reStartInstance(id : String) {
        Timber.tag("reStartInstance").d("RE_START")
        viewModelScope.launch {
            instanceRepository.reStartInstance(id)
                .onSuccess {
                    instanceControlSuccessHandling(it,"Reboot")
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(message = "network error")
                    }
                }
        }
    }

    fun stopInstance(id : String) {
        Timber.tag("stopInstance").d("STOP")
        viewModelScope.launch {
            instanceRepository.stopInstance(id)
                .onSuccess {
                    instanceControlSuccessHandling(it,"Stop")
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(message = "network error")
                    }
                }
        }
    }

    private fun instanceControlSuccessHandling(response : InstanceControlResponse?,action : String){
        _uiState.update { state ->
            if(response != null){
                if (response.isSuccess) state.copy(message = "Instance $action Success!")
                else state.copy(message = response.message)
            }else{
                state.copy(message = "server error")
            }
        }
    }
}
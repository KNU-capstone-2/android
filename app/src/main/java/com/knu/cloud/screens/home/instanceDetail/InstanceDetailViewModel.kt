package com.knu.cloud.screens.home.instanceDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
)

@HiltViewModel
class InstanceDetailViewModel @Inject constructor (
    private val instanceRepository: InstanceRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(InstanceDetailUiState())
    val uiState : StateFlow<InstanceDetailUiState> = _uiState.asStateFlow()

    fun getInstance(instanceId : String){
        Timber.tag("${this.javaClass.name}_getInstance").d("getInstance($instanceId)")
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch{
            instanceRepository.getInstance(instanceId)
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

    fun startInstance(instanceId : String) {
        Timber.tag("startInstance").d("START")
        viewModelScope.launch {
            instanceRepository.startInstance(instanceId)
                .onSuccess {
                    /*TODO*/
                }.onFailure {
                    /*TODO*/
                }
        }
    }

    fun reStartInstance(instanceId : String) {
        Timber.tag("reStartInstance").d("RE_START")
        viewModelScope.launch {
            instanceRepository.reStartInstance(instanceId)
                .onSuccess {
                    /*TODO*/
                }.onFailure {
                    /*TODO*/
                }
        }
    }

    fun stopInstance(instanceId : String) {
        Timber.tag("stopInstance").d("STOP")
        viewModelScope.launch {
            instanceRepository.stopInstance(instanceId)
                .onSuccess {
                    /*TODO*/
                }.onFailure {
                    /*TODO*/
                }
        }
    }
}
package com.knu.cloud.screens.home.instanceDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.repository.instance.InstanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InstanceDetailViewModel @Inject constructor (
    private val instanceRepository: InstanceRepository
) : ViewModel(){
    private val _instance = MutableStateFlow<InstanceData?>(null)
    val instance get() = _instance.asStateFlow()

    fun getInstance(instanceId : String){
        Timber.tag("InstanceViewModel").d("getInstance($instanceId)")
        viewModelScope.launch{
            instanceRepository.getInstance(instanceId)
                .onSuccess { instanceData ->
                    if(instanceData != null ){
                        _instance.value  = instanceData
                    }else{
                        _instance.value = null
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getInstance")
                        .e("message :${it.message} , code :${it.code}")
                }
        }
        Timber.tag("InstanceViewModel").d("getInstance - instance : ${_instance.value}")
    }
}
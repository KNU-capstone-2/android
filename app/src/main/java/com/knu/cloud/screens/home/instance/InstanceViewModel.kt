package com.knu.cloud.screens.home.instance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.components.basicTable.TableRowData
import com.knu.cloud.model.home.instance.InstanceControlResponse
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.model.home.instance.testInstanceDataList
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

data class InstanceUiState(
    val isLoading : Boolean = false,
    val instances : List<InstanceData> = emptyList(),
    val checkedIds : List<String> = emptyList(),
    val deleteComplete : Boolean = false,
    val deleteResult : List<Pair<String,Boolean>> = emptyList(),
    val message : String = "",
)

@HiltViewModel
class InstanceViewModel @Inject constructor (
    private val instanceRepository: InstanceRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(InstanceUiState())
    val uiState :StateFlow<InstanceUiState> = _uiState.asStateFlow()
    init {
//        _uiState.update { it.copy(instances = testInstanceDataList) }
        getAllInstances()
    }

    /**
     * Instance를 전부 가져옴
     */
    private fun getAllInstances(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            instanceRepository.getAllInstances()
                .onSuccess { instanceList ->
                    if (instanceList != null) {
                        _uiState.update { it.copy(instances = instanceList, isLoading = false) }
                    }else{
                        _uiState.update { it.copy(instances = emptyList(), isLoading = false) }
                    }
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(instances = emptyList(), isLoading = false)
                    }
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getAllInstances")
                        .e("message :${it.message} , code :${it.code}")
                }
        }
    }
    /**
     * 체크된 instance들을 하나씩 삭제하고 결과를 deleteResult에 넣는다.
     * uiState는 마지막에 한 번만 업데이트함
     * */
    fun deleteCheckedInstances(){
        Timber.tag("${this.javaClass.name}_deleteCheckedInstances()").d(" : checkedids ${uiState.value.checkedIds} 삭제될 예정")
//        val filteredData = _instances.value.filterNot { it.instancesId in _checkedInstanceIdList.value }                   // 삭제 과정 시뮬레이션

        viewModelScope.launch {
            val deleteSuccessList : MutableList<String> = mutableListOf()
            _uiState.value.checkedIds.forEach{ id ->
                instanceRepository.deleteInstance(id)
                    .onSuccess {
                        deleteSuccessList.add(id)
                    }.onFailure {
                        it as RetrofitFailureStateException
                        Timber.tag("${this.javaClass.name}_getAllInstances")
                            .e("message :${it.message} , code :${it.code}")
                    }
            }
            _uiState.update { state ->
                state.copy(
                    instances = state.instances.filterNot { it.id in deleteSuccessList },                              // 삭제 성공한  리스트에 없는 instances
                    deleteResult = state.checkedIds.map { id ->
                        Pair(getDataFromId(id).instanceName, id in deleteSuccessList)
                    },
                    deleteComplete = true
                )
            }
//            _instances.emit(filteredData)                                                                                      // 삭제 완료 후 처리 시뮬레이션
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
    fun instanceCheck(id : String) {
        if(id !in _uiState.value.checkedIds){
            _uiState.update { it.copy(checkedIds = it.checkedIds + id) }
        }
        Timber.tag("vm_test").d("instanceCheck : checkedids ${uiState.value.checkedIds}")
    }
    fun instanceUncheck(instanceId: String) {
        _uiState.update { state ->
            state.copy(
                checkedIds = state.checkedIds.filterNot { it == instanceId }
            )
        }
        Timber.tag("vm_test").d("instanceUncheck : checkedInstanceIds ${uiState.value.checkedIds}")
    }

    fun allInstanceCheck(allChecked: Boolean) {
        if(allChecked) {
            _uiState.update { state ->
                state.copy( checkedIds = state.instances.map { it.instanceId })
            }
        }else initializeCheckInstanceIds()
        Timber.tag("vm_test").d("allInstanceCheck : checkedInstanceIds ${uiState.value.checkedIds}")
    }

    fun closeDeleteResultDialog(){
        _uiState.update {
            it.copy( deleteComplete = false, checkedIds = emptyList())
        }
    }
    private fun initializeCheckInstanceIds(){
        _uiState.update { state ->
            state.copy(checkedIds = emptyList())
        }
        Timber.tag("vm_test").d("initializeCheckInstanceIds : checkedInstanceIds ${uiState.value.checkedIds}")
    }
    private fun instanceControlSuccessHandling(response : InstanceControlResponse?, action : String){
        _uiState.update { state ->
            if(response != null){
                if (response.isSuccess) state.copy(message = "Instance $action Success!")
                else state.copy(message = response.message)
            }else{
                state.copy(message = "server error")
            }
        }
        getAllInstances()                                                          // 화면 새로 초기화
    }
    private fun getDataFromId(id :String) : InstanceData{
        return _uiState.value.instances.filter { it.id == id}[0]
    }

}

var testTableRowData = mutableListOf(
    TableRowData(
        dataList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
        isRowSelected = false
    ),
    TableRowData(
        dataList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
        isRowSelected = false
    )
)
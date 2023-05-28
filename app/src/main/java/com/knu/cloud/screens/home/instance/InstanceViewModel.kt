package com.knu.cloud.screens.home.instance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.components.basicTable.TableRowData
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

data class InstanceUiState(
    val isLoading : Boolean = false,
    val instances : List<InstanceData> = emptyList(),
    val checkedInstanceIds : List<String> = emptyList(),
    val deleteComplete : Boolean = false,
    val deleteResult : List<Pair<String,Boolean>> = emptyList()
)

@HiltViewModel
class InstanceViewModel @Inject constructor (
    private val instanceRepository: InstanceRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(InstanceUiState())
    val uiState :StateFlow<InstanceUiState> = _uiState.asStateFlow()
    init {
//        _instances.value = testInstanceData
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
        Timber.tag("${this.javaClass.name}_deleteCheckedInstances()").d(" : checkedInstanceIds ${uiState.value.checkedInstanceIds} 삭제될 예정")
//        val filteredData = _instances.value.filterNot { it.instancesId in _checkedInstanceIdList.value }                   // 삭제 과정 시뮬레이션

        viewModelScope.launch {
            val deleteSuccessList : MutableList<String> = mutableListOf()
            _uiState.value.checkedInstanceIds.forEach{ instanceId ->
                instanceRepository.deleteInstance(instanceId)
                    .onSuccess {
                        deleteSuccessList.add(instanceId)
                    }.onFailure {
                        it as RetrofitFailureStateException
                        Timber.tag("${this.javaClass.name}_getAllInstances")
                            .e("message :${it.message} , code :${it.code}")
                    }
            }
            _uiState.update { state ->
                state.copy(
                    instances = state.instances.filterNot { it.instanceId in deleteSuccessList },                              // 삭제 성공한  리스트에 없는 instances
                    deleteResult = state.checkedInstanceIds.map { id ->
                        Pair(id, id in deleteSuccessList)
                    },
                    deleteComplete = true
                )
            }
//            _instances.emit(filteredData)                                                                                      // 삭제 완료 후 처리 시뮬레이션
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

    fun instanceCheck(instanceId : String) {
        if(instanceId !in _uiState.value.checkedInstanceIds){
            _uiState.update { it.copy(checkedInstanceIds = it.checkedInstanceIds + instanceId) }
        }
        Timber.tag("vm_test").d("instanceCheck : checkedInstanceIds ${uiState.value.checkedInstanceIds}")
    }
    fun instanceUncheck(instanceId: String) {
        _uiState.update { state ->
            state.copy(
                checkedInstanceIds = state.checkedInstanceIds.filterNot { it == instanceId }
            )
        }
        Timber.tag("vm_test").d("instanceUncheck : checkedInstanceIds ${uiState.value.checkedInstanceIds}")
    }

    fun allInstanceCheck(allChecked: Boolean) {
        if(allChecked) {
            _uiState.update { state ->
                state.copy( checkedInstanceIds = state.instances.map { it.instanceId })
            }
        }else initializeCheckInstanceIds()
        Timber.tag("vm_test").d("allInstanceCheck : checkedInstanceIds ${uiState.value.checkedInstanceIds}")
    }

    fun closeDeleteResultDialog(){
        _uiState.update {
            it.copy( deleteComplete = false, checkedInstanceIds = emptyList())
        }
    }
    private fun initializeCheckInstanceIds(){
        _uiState.update { state ->
            state.copy(checkedInstanceIds = emptyList())
        }
        Timber.tag("vm_test").d("initializeCheckInstanceIds : checkedInstanceIds ${uiState.value.checkedInstanceIds}")
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
//val testInstanceDataList = mutableListOf(
//    InstanceData(
//        instancesId = "i-0f204053ab80b5cc8",
//        instancesName = "ec2-test",
//        publicIPv4Address = "52.83.423.531",
//        privateIPv4Address = "172.31.5.206",
//        instanceStatus = "Running",
//        publicIPv4DNS = "ec2-52-78-233-109 ap",
//        hostNameType = "ip-173-31-92-94.31.ec2",
//        privateIpDnsName = "IPv4(A)",
//        instanceType = "t2.micro",
//        statusCheck = "2/2 check passe",
//    ),
//    InstanceData(
//        instancesId = "k-fwe31431jtj34442dcc",
//        instancesName = "server-test",
//        publicIPv4Address = "52.83.423.522",
//        privateIPv4Address = "172.31.5.111",
//        instanceStatus = "Running",
//        publicIPv4DNS = "ec2-52-78-233-109 ap",
//        hostNameType = "ip-173-31-92-94.31.ec2",
//        privateIpDnsName = "IPv4(A)",
//        instanceType = "t2.micro",
//        statusCheck = "2/2 check passe",
//    ),
//    InstanceData(
//        instancesId = "i-ac3199341fk33140f3",
//        instancesName = "ec2-pocket-server",
//        publicIPv4Address = "52.83.423.511",
//        privateIPv4Address = "172.31.5.204",
//        instanceStatus = "Stop",
//        publicIPv4DNS = "ec2-52-78-233-109 ap",
//        hostNameType = "ip-173-31-92-94.31.ec2",
//        privateIpDnsName = "IPv4(A)",
//        instanceType = "t2.micro",
//        statusCheck = "2/2 check passe",
//    )
//)
//
//val testInstanceData = InstanceData(
//    instancesId = "i-0f204053ab80b5cc8",
//    instancesName = "ec2-test",
//    publicIPv4Address = "52.83.423.531",
//    privateIPv4Address = "172.31.5.206",
//    instanceStatus = "Running",
//    publicIPv4DNS = "ec2-52-78-233-109 ap",
//    hostNameType = "ip-173-31-92-94.31.ec2",
//    privateIpDnsName = "ip-172-31-92.42.ec2.internal",
//    instanceType = "t2.micro",
//    statusCheck = "2/2 check passe"
//)
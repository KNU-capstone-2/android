package com.knu.cloud.screens.home.instance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.components.basicTable.TableRowData
import com.knu.cloud.model.instance.InstanceData
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.repository.InstanceRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject



/*TODO : ViewState 처리해주기
* @Composable
fun MyScreen(viewModel: MyViewModel) {
    val viewState by viewModel.viewState.collectAsState()
    when (viewState) {
        is ViewState.Loading -> {
            CircularProgressIndicator()
        }
        is ViewState.Loaded -> {
            val data = (viewState as ViewState.Loaded).data
            GridView(data = data)
        }
        is ViewState.Error -> {
            val errorMessage = (viewState as ViewState.Error).message
            // show error message
        }
    }

    // request data on initial launch
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }
}
* */


@HiltViewModel
class InstanceViewModel @Inject constructor (
    private val instanceRepository: InstanceRepositoryImpl
) : ViewModel(){

    private val _instances = MutableStateFlow<List<InstanceData>>(emptyList())
    val instances get() = _instances.asStateFlow()

    private val _instance = MutableStateFlow<InstanceData?>(null)
    val instance get() = _instance.asStateFlow()

    private val _checkedInstanceIdList = MutableStateFlow<List<String>>(emptyList())
    val checkedInstanceData : StateFlow<List<String>> = _checkedInstanceIdList.asStateFlow()

    init {
//        _instances.value = testInstanceData
        getAllInstances()
    }

    /**
     * Instance를 전부 가져옴
     */
    private fun getAllInstances(){
        viewModelScope.launch {
            instanceRepository.getAllInstances()
                .onSuccess { instanceList ->
                    if (instanceList != null) {
                        _instances.value = instanceList
                    }else{
                        _instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getAllInstances")
                        .e("message :${it.message} , code :${it.code}")
                }
        }
    }
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
    fun deleteCheckedInstances(){
        /*TODO : Repository의 deleteInstances 함수 호출*/
        Timber.tag("vm_test").d("deleteCheckedInstances ${_checkedInstanceIdList.value}가 삭제될 예정")

        val filteredData = _instances.value.filterNot { it.instancesId in _checkedInstanceIdList.value }

        viewModelScope.launch {
            _instances.emit(filteredData)
            _checkedInstanceIdList.emit(listOf())
        }
    }
    fun instanceCheck(instanceId : String) {
        viewModelScope.launch {
            _checkedInstanceIdList.emit(_checkedInstanceIdList.value +instanceId)
            Timber.tag("vm_test").d("_checkedInstanceIdList ${_checkedInstanceIdList.value}")
        }
    }
    fun instanceUncheck(instanceId: String) {
        viewModelScope.launch {
            _checkedInstanceIdList.emit(_checkedInstanceIdList.value.filterNot { it == instanceId })
            Timber.tag("vm_test").d("_checkedInstanceIdList ${_checkedInstanceIdList.value}")
        }
    }

    fun allInstanceCheck(allChecked: Boolean) {
        viewModelScope.launch {
            if(allChecked){
                _checkedInstanceIdList.emit(_instances.value.map{it.instancesId})
            }else{
                _checkedInstanceIdList.emit(listOf())
            }
            Timber.tag("vm_test").d("_checkedInstanceIdList ${_checkedInstanceIdList.value}")
        }
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
val testInstanceDataList = mutableListOf(
    InstanceData(
        instancesId = "i-0f204053ab80b5cc8",
        instancesName = "ec2-test",
        publicIPv4Address = "52.83.423.531",
        privateIPv4Address = "172.31.5.206",
        instanceState = "Running",
        publicIPv4DNS = "ec2-52-78-233-109 ap",
        hostNameType = "ip-173-31-92-94.31.ec2",
        privateIpDnsName = "IPv4(A)",
        instanceType = "t2.micro",
        statusCheck = "2/2 check passe",
    ),
    InstanceData(
        instancesId = "k-fwe31431jtj34442dcc",
        instancesName = "server-test",
        publicIPv4Address = "52.83.423.522",
        privateIPv4Address = "172.31.5.111",
        instanceState = "Running",
        publicIPv4DNS = "ec2-52-78-233-109 ap",
        hostNameType = "ip-173-31-92-94.31.ec2",
        privateIpDnsName = "IPv4(A)",
        instanceType = "t2.micro",
        statusCheck = "2/2 check passe",
    ),
    InstanceData(
        instancesId = "i-ac3199341fk33140f3",
        instancesName = "ec2-pocket-server",
        publicIPv4Address = "52.83.423.511",
        privateIPv4Address = "172.31.5.204",
        instanceState = "Stop",
        publicIPv4DNS = "ec2-52-78-233-109 ap",
        hostNameType = "ip-173-31-92-94.31.ec2",
        privateIpDnsName = "IPv4(A)",
        instanceType = "t2.micro",
        statusCheck = "2/2 check passe",
    )
)

val testInstanceData = InstanceData(
    instancesId = "i-0f204053ab80b5cc8",
    instancesName = "ec2-test",
    publicIPv4Address = "52.83.423.531",
    privateIPv4Address = "172.31.5.206",
    instanceState = "Running",
    publicIPv4DNS = "ec2-52-78-233-109 ap",
    hostNameType = "ip-173-31-92-94.31.ec2",
    privateIpDnsName = "ip-172-31-92.42.ec2.internal",
    instanceType = "t2.micro",
    statusCheck = "2/2 check passe"
)
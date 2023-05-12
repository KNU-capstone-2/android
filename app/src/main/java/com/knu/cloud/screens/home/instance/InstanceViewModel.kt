package com.knu.cloud.screens.home.instance

import android.os.Parcelable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.knu.cloud.di.ApplicationScopeDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import javax.inject.Inject

@Parcelize
data class InstanceData(
    val instancesId: String,
    val instancesName: String,
    val publicIPv4Address: String,
    val privateIPv4Address: String,
    val instanceState: String,
    val publicIPv4DNS: String,
    val hostNameType: String,
    val privateIpDNSname: String,
    val instanceType: String,
    val statusCheck : String,
): Parcelable

data class TableRowData(
    val dataList : List<String>,
    var isRowSelected : Boolean
)

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

val testInstanceData = mutableListOf(
    InstanceData(
        instancesId = "i-0f204053ab80b5cc8",
        instancesName = "ec2-test",
        publicIPv4Address = "52.83.423.531",
        privateIPv4Address = "172.31.5.206",
        instanceState = "Running",
        publicIPv4DNS = "ec2-52-78-233-109 ap",
        hostNameType = "ip-173-31-92-94.31.ec2",
        privateIpDNSname = "IPv4(A)",
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
        privateIpDNSname = "IPv4(A)",
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
        privateIpDNSname = "IPv4(A)",
        instanceType = "t2.micro",
        statusCheck = "2/2 check passe",
    )
)

@HiltViewModel
class InstanceViewModel @Inject constructor (
    @ApplicationScopeDefault private val coroutineScope: CoroutineScope
        ) : ViewModel(){

    private val _testData = MutableStateFlow<List<InstanceData>>(emptyList())
    val testData get() = _testData.asStateFlow()

    private val _checkedInstanceIdList = MutableStateFlow<List<String>>(emptyList())
    val checkedInstanceData : StateFlow<List<String>> = _checkedInstanceIdList.asStateFlow()

    init {
        _testData.value = testInstanceData
    }
    fun instanceCheck(instanceId : String) {
        coroutineScope.launch {
            _checkedInstanceIdList.emit(_checkedInstanceIdList.value +instanceId)
            Timber.tag("vm_test").d("_checkedInstanceIdList ${_checkedInstanceIdList.value}")
        }
    }
    fun instanceUncheck(instanceId: String) {
        coroutineScope.launch {
            _checkedInstanceIdList.emit(_checkedInstanceIdList.value.filterNot { it == instanceId })
            Timber.tag("vm_test").d("_checkedInstanceIdList ${_checkedInstanceIdList.value}")
        }
    }

    fun allInstanceCheck(allChecked: Boolean) {
        coroutineScope.launch {
            if(allChecked){
                _checkedInstanceIdList.emit(_testData.value.map{it.instancesId})
            }else{
                _checkedInstanceIdList.emit(listOf())
            }
            Timber.tag("vm_test").d("_checkedInstanceIdList ${_checkedInstanceIdList.value}")
        }
    }

    fun deleteCheckedInstances(){
        /*TODO : Repository의 deleteInstances 함수 호출*/
        Timber.tag("vm_test").d("deleteCheckedInstances ${_checkedInstanceIdList.value}가 삭제될 예정")

        val filteredData = _testData.value.filterNot { it.instancesId in _checkedInstanceIdList.value }

        coroutineScope.launch {
            _testData.emit(filteredData)
            _checkedInstanceIdList.emit(listOf())
        }
    }

}
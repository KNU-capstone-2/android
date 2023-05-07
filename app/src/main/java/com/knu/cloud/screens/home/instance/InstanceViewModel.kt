package com.knu.cloud.screens.home.instance

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
    var isSelected: Boolean
)

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
        isSelected = false
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
        isSelected = false
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
        isSelected = false
    )
)

@HiltViewModel
class InstanceViewModel @Inject constructor () : ViewModel(){

    private val _testData = mutableStateOf<List<InstanceData>>(emptyList())
    val testData : State<List<InstanceData>> = _testData

    init {
        _testData.value = testInstanceData
    }

}
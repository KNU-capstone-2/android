package com.knu.cloud.screens.instanceCreate

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.dialog.CreateInstanceState
import com.knu.cloud.model.instanceCreate.FlavorResponse
import com.knu.cloud.model.instanceCreate.KeypairResponse
import com.knu.cloud.model.instanceCreate.NetworkResponse
import com.knu.cloud.model.instanceCreate.SourceResponse
import com.knu.cloud.repository.instanceCreate.InstanceCreateRepository
import com.knu.cloud.utils.RetrofitFailureStateException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InstanceCreateViewModel @Inject constructor(
    private val instanceCreateRepository: InstanceCreateRepository
): ViewModel() {

    private val _isDialogOpen = mutableStateOf(false)
    val isDialogOpen :State<Boolean> = _isDialogOpen

    /* 리소스 프로비저닝 Dialog box */
    private val _openResourceDialog = MutableStateFlow<CreateInstanceState>(CreateInstanceState(showProgressDialog = false))
    val openResourceDialog: StateFlow<CreateInstanceState>
        get() = _openResourceDialog.asStateFlow()

    private val _uploadFlavor = mutableStateOf<List<FlavorResponse>>(emptyList())
    val uploadFlavor: State<List<FlavorResponse>> = _uploadFlavor
    private val _possibleFlavor = mutableStateOf<List<FlavorResponse>>(emptyList())
    val possibleFlavor: State<List<FlavorResponse>> = _possibleFlavor

    private val _uploadSource = mutableStateOf<List<SourceResponse>>(emptyList())
    val uploadSource: State<List<SourceResponse>> = _uploadSource
    private val _possibleSource = mutableStateOf<List<SourceResponse>>(emptyList())
    val possibleSource: State<List<SourceResponse>> = _possibleSource

    private val _uploadNetwork = mutableStateOf<List<NetworkResponse>>(emptyList())
    val uploadNetwork: State<List<NetworkResponse>> = _uploadNetwork
    private val _possibleNetwork = mutableStateOf<List<NetworkResponse>>(emptyList())
    val possibleNetwork: State<List<NetworkResponse>> = _possibleNetwork

    private val _uploadKeypair = mutableStateOf<List<KeypairResponse>>(emptyList())
    val uploadKeypair: State<List<KeypairResponse>> = _uploadKeypair
    private val _possibleKeypair = mutableStateOf<List<KeypairResponse>>(emptyList())
    val possibleKeypair: State<List<KeypairResponse>> = _possibleKeypair

    private val keyName = MutableStateFlow("")
    private val keyType = MutableStateFlow("")

    var uploadFlavorDataSet = mutableListOf<FlavorResponse>()
    var uploadSourceDataSet = mutableListOf<SourceResponse>()
    var uploadNetworkDataSet = mutableListOf<NetworkResponse>()
    var uploadKeypairDataSet = mutableListOf<KeypairResponse>()

    /*
    var possibleFlavorDataSet = mutableListOf(
        FlavorResponse("m1.nano", 1, 128, 1, 1, 0, "예"),
        FlavorResponse("m1.micro", 1, 192, 1, 1, 0, "예"),
        FlavorResponse("cirros256", 1, 256, 1, 1, 0,"예"),
        FlavorResponse("m1.tiny", 1, 512, 1, 1, 0, "예"),
        FlavorResponse("ds512M", 1, 512 , 5, 5, 0 , "예"),
        FlavorResponse("ds1G", 1, 1, 10, 10, 0, "예"),
        FlavorResponse("m1.small", 1, 2, 20, 20, 0, "예"),
        FlavorResponse("ds2G", 2, 2, 10, 10, 0, "예"),
        FlavorResponse("m1.medium", 2, 4, 40, 40, 0, "예"),
        FlavorResponse("ds4G",  4, 4, 20, 20, 0, "예"),
        FlavorResponse("m1.large", 4, 8, 80, 80, 0, "예"),
        FlavorResponse("m1.xlarge", 8, 16, 160, 160, 0, "예")
    )

    var possibleSourceDataSet = mutableListOf(
        SourceResponse("cirros-0.5.2-x86_64-disk", "3/13/23 7:31 AM", 15.55, "QCOW2", "공용"),
        SourceResponse("nano-1-x86_64-disk", "3/24/23 11:13 PM", 5.55, "QCOW2", "공용")
    )

    var possibleNetworkDataSet = mutableListOf(
        NetworkResponse("shared", "shared-subnet","예", "Up", "Active"),
        NetworkResponse("private", "ipv6-private-subnet private-subnet","아니오", "Up", "Active")
    )

    var possibleKeypairDataSet = mutableListOf(
        KeypairResponse("test-pocket", "ssh"),
        KeypairResponse("test-1", "ssh")
    )
    */
    private var possibleFlavorDataSet: MutableList<FlavorResponse> = mutableListOf()
    private var possibleKeypairDataSet: MutableList<KeypairResponse> = mutableListOf()
    private var possibleNetworkDataSet: MutableList<NetworkResponse> = mutableListOf()
    private var possibleSourceDataSet: MutableList<SourceResponse> = mutableListOf()

    init {
        getAllFlavorData()
        getAllKeypairData()
        getAllNetworkData()
        getAllSourceData()
    }

    fun uploadFlavor(flavor: FlavorResponse, position: Int) {
        uploadFlavorDataSet.add(flavor)
        _uploadFlavor.value = uploadFlavorDataSet.toMutableStateList()
        possibleFlavorDataSet.removeAt(position)
        _possibleFlavor.value = possibleFlavorDataSet.toMutableStateList()
    }

    fun deleteFlavor(flavor: FlavorResponse, position: Int) {
        uploadFlavorDataSet.removeAt(position)
        _uploadFlavor.value = uploadFlavorDataSet.toMutableStateList()
        possibleFlavorDataSet.add(flavor)
        _possibleFlavor.value = possibleFlavorDataSet.toMutableStateList()
    }

    fun uploadSource(sourceResponse: SourceResponse, position: Int) {
        uploadSourceDataSet.add(sourceResponse)
        _uploadSource.value = uploadSourceDataSet.toMutableStateList()
        possibleSourceDataSet.removeAt(position)
        _possibleSource.value = possibleSourceDataSet.toMutableStateList()
    }

    fun deleteSource(sourceResponse: SourceResponse, position: Int) {
        uploadSourceDataSet.removeAt(position)
        _uploadSource.value = uploadSourceDataSet.toMutableStateList()
        possibleSourceDataSet.add(sourceResponse)
        _possibleSource.value = possibleSourceDataSet.toMutableStateList()
    }

    fun uploadNetwork(network: NetworkResponse, position: Int) {
        uploadNetworkDataSet.add(network)
        _uploadNetwork.value = uploadNetworkDataSet.toMutableStateList()
        possibleNetworkDataSet.removeAt(position)
        _possibleNetwork.value = possibleNetworkDataSet.toMutableStateList()
    }

    fun deleteNetwork(network: NetworkResponse, position: Int) {
        uploadNetworkDataSet.removeAt(position)
        _uploadNetwork.value = uploadNetworkDataSet.toMutableStateList()
        possibleNetworkDataSet.add(network)
        _possibleNetwork.value = possibleNetworkDataSet.toMutableStateList()
    }

    fun uploadKeypair(keypairResponse: KeypairResponse, position: Int) {
        uploadKeypairDataSet.add(keypairResponse)
        _uploadKeypair.value = uploadKeypairDataSet.toMutableStateList()
        possibleKeypairDataSet.removeAt(position)
        _possibleKeypair.value = possibleKeypairDataSet.toMutableStateList()
    }

    fun deleteKeypair(keypairResponse: KeypairResponse, position: Int) {
        uploadKeypairDataSet.removeAt(position)
        _uploadKeypair.value = uploadKeypairDataSet.toMutableStateList()
        possibleKeypairDataSet.add(keypairResponse)
        _possibleKeypair.value = possibleKeypairDataSet.toMutableStateList()
    }

    /* KeyPairScreen */
    fun setKeyName(name: String) {
        keyName.value = name
        Timber.tag("viewModel_KeypairScreen").e(keyName.value)
    }

    fun setKeyType(type: String) {
        keyType.value = type
        Timber.tag("viewModel_KeypairScreen").e(keyType.value)
    }

    fun openDialog() {
        _isDialogOpen.value = true
        Timber.tag("dialog").d("_isDialogOpen : ${_isDialogOpen.value}")
//        viewModelScope.launch {
//            _openResourceDialog.update { state ->
//                state.copy(showProgressDialog = true)
//            }
//        }
    }

    fun startCoroutine(
        context: Context
    ) {
        viewModelScope.launch {
            // Do the background work here
            delay(3000)
            closeDialog(context)
        }
    }
    fun updateOpenResourceDialog(openResourceDialog: CreateInstanceState) {
        Timber.tag("update").e("${openResourceDialog.showProgressDialog}")
        viewModelScope.launch {
            _openResourceDialog.update {
                it.copy(showProgressDialog = openResourceDialog.showProgressDialog)
            }
        }
    }
    private fun closeDialog(
        context: Context
    ) {
        _isDialogOpen.value = false
//        viewModelScope.launch {
//            _openResourceDialog.update { state ->
//                state.copy(showProgressDialog = false)
//            }
//        }
        Toast.makeText(context, "리소스 프로지버닝 완료!", Toast.LENGTH_SHORT).show()
    }

    private fun getAllFlavorData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllFlavorData()
                .onSuccess { it ->
                    if (it != null) {
                        possibleFlavorDataSet = it.toMutableList()
                    }else{
                       //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getAllInstances")
                        .e("message :${it.message} , code :${it.code}")
                }
            _uploadFlavor.value = uploadFlavorDataSet
            _possibleFlavor.value = possibleFlavorDataSet
        }
    }

    private fun getAllKeypairData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllKeypairData()
                .onSuccess { it ->
                    if (it != null) {
                        possibleKeypairDataSet = it.toMutableList()
                    }else{
                        //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getAllInstances")
                        .e("message :${it.message} , code :${it.code}")
                }
            _uploadKeypair.value = uploadKeypairDataSet
            _possibleKeypair.value = possibleKeypairDataSet
        }
    }

    private fun getAllNetworkData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllNetworkData()
                .onSuccess { it ->
                    if (it != null) {
                        possibleNetworkDataSet = it.toMutableList()
                    }else{
                        //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getAllInstances")
                        .e("message :${it.message} , code :${it.code}")
                }
            _uploadNetwork.value = uploadNetworkDataSet
            _possibleNetwork.value = possibleNetworkDataSet
        }
    }

    private fun getAllSourceData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllSourceData()
                .onSuccess { it ->
                    if (it != null) {
                        possibleSourceDataSet = it.toMutableList()
                    }else{
                        //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.tag("${this.javaClass.name}_getAllInstances")
                        .e("message :${it.message} , code :${it.code}")
                }
            _uploadSource.value = uploadSourceDataSet
            _possibleSource.value = possibleSourceDataSet
        }
    }
}


package com.knu.cloud.screens.instanceCreate

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.dialog.CreateInstanceState
import com.knu.cloud.model.instanceCreate.Flavor
import com.knu.cloud.model.instanceCreate.Keypair
import com.knu.cloud.model.instanceCreate.Network
import com.knu.cloud.model.instanceCreate.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

var uploadFlavorDataSet = mutableListOf<Flavor>()
var possibleFlavorDataSet = mutableListOf(
    Flavor("m1.nano", 1, 128, 1, 1, 0, "예"),
    Flavor("m1.micro", 1, 192, 1, 1, 0, "예"),
    Flavor("cirros256", 1, 256, 1, 1, 0,"예"),
    Flavor("m1.tiny", 1, 512, 1, 1, 0, "예"),
    Flavor("ds512M", 1, 512 , 5, 5, 0 , "예"),
    Flavor("ds1G", 1, 1, 10, 10, 0, "예"),
    Flavor("m1.small", 1, 2, 20, 20, 0, "예"),
    Flavor("ds2G", 2, 2, 10, 10, 0, "예"),
    Flavor("m1.medium", 2, 4, 40, 40, 0, "예"),
    Flavor("ds4G",  4, 4, 20, 20, 0, "예"),
    Flavor("m1.large", 4, 8, 80, 80, 0, "예"),
    Flavor("m1.xlarge", 8, 16, 160, 160, 0, "예")
)

var uploadSourceDataSet = mutableListOf<Source>()
var possibleSourceDataSet = mutableListOf(
    Source("cirros-0.5.2-x86_64-disk", "3/13/23 7:31 AM", 15.55, "QCOW2", "공용"),
    Source("nano-1-x86_64-disk", "3/24/23 11:13 PM", 5.55, "QCOW2", "공용")
)

var uploadNetworkDataSet = mutableListOf<Network>()
var possibleNetworkDataSet = mutableListOf(
    Network("shared", "shared-subnet","예", "Up", "Active"),
    Network("private", "ipv6-private-subnet private-subnet","아니오", "Up", "Active")
)

var uploadKeypairDataSet = mutableListOf<Keypair>()
var possibleKeypairDataSet = mutableListOf(
    Keypair("test-pocket", "ssh"),
    Keypair("test-1", "ssh")
)

@HiltViewModel
class InstanceCreateViewModel @Inject constructor(

): ViewModel() {

    /* 리소스 프로비저닝 Dialog box */
    private val _openResourceDialog = MutableStateFlow<CreateInstanceState>(CreateInstanceState(showProgressDialog = false))
    val openResourceDialog: StateFlow<CreateInstanceState>
        get() = _openResourceDialog.asStateFlow()

    private val _uploadFlavor = mutableStateOf<List<Flavor>>(emptyList())
    val uploadFlavor: State<List<Flavor>> = _uploadFlavor
    private val _possibleFlavor = mutableStateOf<List<Flavor>>(emptyList())
    val possibleFlavor: State<List<Flavor>> = _possibleFlavor

    private val _uploadSource = mutableStateOf<List<Source>>(emptyList())
    val uploadSource: State<List<Source>> = _uploadSource
    private val _possibleSource = mutableStateOf<List<Source>>(emptyList())
    val possibleSource: State<List<Source>> = _possibleSource

    private val _uploadNetwork = mutableStateOf<List<Network>>(emptyList())
    val uploadNetwork: State<List<Network>> = _uploadNetwork
    private val _possibleNetwork = mutableStateOf<List<Network>>(emptyList())
    val possibleNetwork: State<List<Network>> = _possibleNetwork

    private val _uploadKeypair = mutableStateOf<List<Keypair>>(emptyList())
    val uploadKeypair: State<List<Keypair>> = _uploadKeypair
    private val _possibleKeypair = mutableStateOf<List<Keypair>>(emptyList())
    val possibleKeypair: State<List<Keypair>> = _possibleKeypair

    private val keyName = MutableStateFlow("")
    private val keyType = MutableStateFlow("")

    init {
        _uploadFlavor.value = uploadFlavorDataSet
        _possibleFlavor.value = possibleFlavorDataSet

        _uploadSource.value = uploadSourceDataSet
        _possibleSource.value = possibleSourceDataSet

        _uploadNetwork.value = uploadNetworkDataSet
        _possibleNetwork.value = possibleNetworkDataSet

        _uploadKeypair.value = uploadKeypairDataSet
        _possibleKeypair.value = possibleKeypairDataSet
    }

    fun uploadFlavor(flavor: Flavor, position: Int) {
        uploadFlavorDataSet.add(flavor)
        _uploadFlavor.value = uploadFlavorDataSet.toMutableStateList()
        possibleFlavorDataSet.removeAt(position)
        _possibleFlavor.value = possibleFlavorDataSet.toMutableStateList()
    }

    fun deleteFlavor(flavor: Flavor, position: Int) {
        uploadFlavorDataSet.removeAt(position)
        _uploadFlavor.value = uploadFlavorDataSet.toMutableStateList()
        possibleFlavorDataSet.add(flavor)
        _possibleFlavor.value = possibleFlavorDataSet.toMutableStateList()
    }

    fun uploadSource(source: Source, position: Int) {
        uploadSourceDataSet.add(source)
        _uploadSource.value = uploadSourceDataSet.toMutableStateList()
        possibleSourceDataSet.removeAt(position)
        _possibleSource.value = possibleSourceDataSet.toMutableStateList()
    }

    fun deleteSource(source: Source, position: Int) {
        uploadSourceDataSet.removeAt(position)
        _uploadSource.value = uploadSourceDataSet.toMutableStateList()
        possibleSourceDataSet.add(source)
        _possibleSource.value = possibleSourceDataSet.toMutableStateList()
    }

    fun uploadNetwork(network: Network, position: Int) {
        uploadNetworkDataSet.add(network)
        _uploadNetwork.value = uploadNetworkDataSet.toMutableStateList()
        possibleNetworkDataSet.removeAt(position)
        _possibleNetwork.value = possibleNetworkDataSet.toMutableStateList()
    }

    fun deleteNetwork(network: Network, position: Int) {
        uploadNetworkDataSet.removeAt(position)
        _uploadNetwork.value = uploadNetworkDataSet.toMutableStateList()
        possibleNetworkDataSet.add(network)
        _possibleNetwork.value = possibleNetworkDataSet.toMutableStateList()
    }

    fun uploadKeypair(keypair: Keypair, position: Int) {
        uploadKeypairDataSet.add(keypair)
        _uploadKeypair.value = uploadKeypairDataSet.toMutableStateList()
        possibleKeypairDataSet.removeAt(position)
        _possibleKeypair.value = possibleKeypairDataSet.toMutableStateList()
    }

    fun deleteKeypair(keypair: Keypair, position: Int) {
        uploadKeypairDataSet.removeAt(position)
        _uploadKeypair.value = uploadKeypairDataSet.toMutableStateList()
        possibleKeypairDataSet.add(keypair)
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
        viewModelScope.launch {
            _openResourceDialog.update { state ->
                state.copy(showProgressDialog = true)
            }
        }
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
        viewModelScope.launch {
            _openResourceDialog.update { state ->
                state.copy(showProgressDialog = false)
            }
        }
        Toast.makeText(context, "리소스 프로지버닝 완료!", Toast.LENGTH_SHORT).show()
    }
}


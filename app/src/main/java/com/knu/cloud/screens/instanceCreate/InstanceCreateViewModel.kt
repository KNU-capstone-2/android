package com.knu.cloud.screens.instanceCreate

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.dialog.CreateInstanceDialogState
import com.knu.cloud.model.instanceCreate.*
import com.knu.cloud.model.keypair.KeypairCreateRequest
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.repository.home.dashboard.DashboardRepository
import com.knu.cloud.repository.home.keypair.KeypairRepository
import com.knu.cloud.repository.instanceCreate.InstanceCreateRepository
import com.knu.cloud.utils.ToastStatus
import com.knu.cloud.utils.showMotionToastMessage
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
    private val instanceCreateRepository: InstanceCreateRepository,
    private val dashboardRepository: DashboardRepository,
    private val keypairRepository: KeypairRepository
): ViewModel() {

    /**
     * InstanceCreateDialog 열린 상태
     * */
    private val _isDialogOpen = mutableStateOf(true)
    val isDialogOpen :State<Boolean> = _isDialogOpen

    private val _detailUiState = MutableStateFlow(InstanceCreateDetailUiState())
    val detailUiState : StateFlow<InstanceCreateDetailUiState> = _detailUiState.asStateFlow()

    private val _flavorUiState = MutableStateFlow(InstanceCreateFlavorUiState())
    val flavorUiState :StateFlow<InstanceCreateFlavorUiState> = _flavorUiState.asStateFlow()

    private val _sourceUiState = MutableStateFlow(InstanceCreateSourceUiState())
    val sourceUiState :StateFlow<InstanceCreateSourceUiState> = _sourceUiState.asStateFlow()

    private val _networkUiState = MutableStateFlow(InstanceCreateNetworkUiState())
    val networkUiState :StateFlow<InstanceCreateNetworkUiState> = _networkUiState.asStateFlow()

    private val _keypairUiState = MutableStateFlow(InstanceCreateKeypairUiState())
    val keypairUiState :StateFlow<InstanceCreateKeypairUiState> = _keypairUiState.asStateFlow()

    /* 리소스 프로비저닝 Dialog box */
    private val _createInstanceDialogState = MutableStateFlow(CreateInstanceDialogState())
    val createInstanceDialogState: StateFlow<CreateInstanceDialogState> = _createInstanceDialogState.asStateFlow()

    private val _showCreateKeypairDialog = mutableStateOf(false)
    val showCreateKeypiarDialog :State<Boolean> = _showCreateKeypairDialog

    init {
        getInstanceData()
        getAllFlavorData()
        getAllKeypairData()
        getAllNetworkData()
        getAllSourceData()
    }
    fun createKeypair(keypairCreateRequest: KeypairCreateRequest) {
        viewModelScope.launch {
            keypairRepository.createKeypair(keypairCreateRequest)
                .onSuccess {
                    Timber.d("onSuccess KeypairCreateResponse : $it")
                    getAllKeypairData()
                }
                .onFailure {
                    Timber.d("onFailure : ${it.message}")
                }
            _showCreateKeypairDialog.value = false
        }
    }
    fun createInstance(
        context: Context,
//        activity :Activity
    ) {
        try{
            val createRequest = CreateRequest(
                serverName = detailUiState.value.instanceName,
                imageName = sourceUiState.value.uploadSource!!.name,
                flavorName = flavorUiState.value.uploadFlavor!!.name,
                networkName = networkUiState.value.uploadNetwork!!.network,
                keypairName = keypairUiState.value.uploadKeypair!!.name,
            )
            viewModelScope.launch {
                delay(1500)
                instanceCreateRepository.createInstance(createRequest)
                    .onSuccess {
                        Timber.tag("instanceCreate").d("Success instanceData : $it")
                        if(it != null){
                            _createInstanceDialogState.update { state ->
                                state.copy(
                                    showProgressDialog = false,
                                    message = "${it.instanceName} 생성 완료",
                                    toastStatus = ToastStatus.SUCCESS
                                )
                            }
                            _isDialogOpen.value = false
                        }else{
                            _createInstanceDialogState.update { state ->
                                state.copy(
                                    showProgressDialog = false,
                                    message = "동일한 이름의 인스턴스가 있습니다",
                                    toastStatus = ToastStatus.ERROR
                                )
                            }
//                            closeCreateInstanceDialog(context,ToastStatus.ERROR,"동일한 이름의 인스턴스가 있습니다")
                        }
                    }.onFailure {
                        Timber.tag("instanceCreate").d("Failure : $it")
                        _createInstanceDialogState.update { state ->
                            state.copy(
                                showProgressDialog = false,
                                message = "인스턴스 생성 실패",
                                toastStatus = ToastStatus.ERROR
                            )
                        }
//                        closeCreateInstanceDialog(context,ToastStatus.ERROR,"인스턴스 생성 실패")
                    }
            }
        }catch(e : Exception){
            _createInstanceDialogState.update { state ->
                state.copy(
                    showProgressDialog = false,
                    message = "인스턴스 생성에 필요한 요소를 전부 선택하세요",
                    toastStatus = ToastStatus.ERROR
                )
            }
//            closeCreateInstanceDialog(context,ToastStatus.INFO,"인스턴스 생성에 필요한 요소를 전부 선택하세요")
        }
    }

    private fun showToast( context: Context,text : String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    fun openCreateInstanceDialog() {
        _createInstanceDialogState.update { state ->
            state.copy(showProgressDialog = true)
        }
        Timber.d("showProgressDialog : ${_createInstanceDialogState.value.showProgressDialog}")
    }
    private fun closeCreateInstanceDialog(
//        context: Context,
        activity: Activity,
        status : ToastStatus,
        text : String
    ) {
        _createInstanceDialogState.update { state ->
            state.copy(showProgressDialog = false)
        }
        showMotionToastMessage(activity,status,text)
        Timber.d("showProgressDialog : ${_createInstanceDialogState.value.showProgressDialog}")
//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showCreateKeypairDialog(){
        _showCreateKeypairDialog.value = true
    }
    fun closeCreateKeypairDialog(){
        _showCreateKeypairDialog.value = false
    }

    fun uploadFlavor(flavor: FlavorData, index : Int){
        _flavorUiState.update { state ->
             state.copy(
                 uploadFlavor = flavor,
                 uploadFlavorIndex = index,
                 possibleFlavors = state.possibleFlavors.filterIndexed { idx, _->  idx != index }
             )
        }
        Timber.d("index : $index")
        Timber.d("upload possibleFlavors : ${_flavorUiState.value.possibleFlavors.map { it.name }}")
    }
    fun updateFlavor(flavor: FlavorData, index : Int){
        val temp = _flavorUiState.value.uploadFlavor!!
        _flavorUiState.update { state ->
            val updatePossibleFlavors = state.possibleFlavors.toMutableList()
            updatePossibleFlavors.add(state.uploadFlavorIndex,temp)
            state.copy(
                uploadFlavor = flavor,
                uploadFlavorIndex = if(index>=state.uploadFlavorIndex) index+1 else index,
                possibleFlavors = updatePossibleFlavors.filterNot { it == flavor }
            )
        }
    }
    fun deleteFlavor(flavor: FlavorData) {
        _flavorUiState.update { state ->
            val updatePossibleFlavors = state.possibleFlavors.toMutableList()
            updatePossibleFlavors.add(state.uploadFlavorIndex,flavor)
            state.copy(
                uploadFlavor = null,
                possibleFlavors = updatePossibleFlavors
            )
        }
        Timber.d("delete possibleFlavors : ${_flavorUiState.value.possibleFlavors.map { it.name }}")
    }

    fun uploadSource(source: ImageData, index : Int){
        _sourceUiState.update { state ->
            state.copy(
                uploadSource = source,
                uploadSourceIndex = index,
                possibleSources = state.possibleSources.filterIndexed { idx, _->  idx != index }
            )
        }
        Timber.d("index : $index")
        Timber.d("upload possibleFlavors : ${_flavorUiState.value.possibleFlavors.map { it.name }}")
    }
    fun updateSource(source: ImageData, index : Int){
        val temp = _sourceUiState.value.uploadSource!!
        _sourceUiState.update { state ->
            val updatePossibleSources = state.possibleSources.toMutableList()
            updatePossibleSources.add(state.uploadSourceIndex,temp)
            state.copy(
                uploadSource = source,
                uploadSourceIndex = if(index>=state.uploadSourceIndex) index+1 else index,
                possibleSources = updatePossibleSources.filterNot { it == source }
            )
        }
    }
    fun deleteSource(source: ImageData) {
        _sourceUiState.update { state ->
            val updatePossibleSources = state.possibleSources.toMutableList()
            updatePossibleSources.add(state.uploadSourceIndex,source)
            state.copy(
                uploadSource = null,
                possibleSources = updatePossibleSources
            )
        }
        Timber.d("delete possibleSources : ${_sourceUiState.value.possibleSources.map { it.name }}")
    }

    fun uploadNetwork(network: NetworkData, index : Int){
        _networkUiState.update { state ->
            state.copy(
                uploadNetwork = network,
                uploadNetworkIndex = index,
                possibleNetworks = state.possibleNetworks.filterIndexed { idx, _->  idx != index }
            )
        }
        Timber.d("index : $index")
        Timber.d("upload possibleNetworks : ${_networkUiState.value.possibleNetworks.map { it.network }}")
    }
    fun updateNetwork(network: NetworkData, index : Int){
        val temp = _networkUiState.value.uploadNetwork!!
        _networkUiState.update { state ->
            val updatePossibleNetworks = state.possibleNetworks.toMutableList()
            updatePossibleNetworks.add(state.uploadNetworkIndex,temp)
            state.copy(
                uploadNetwork = network,
                uploadNetworkIndex = if(index>=state.uploadNetworkIndex) index+1 else index,
                possibleNetworks = updatePossibleNetworks.filterNot { it == network }
            )
        }
    }
    fun deleteNetwork(network: NetworkData) {
        _networkUiState.update { state ->
            val updatePossibleNetworks = state.possibleNetworks.toMutableList()
            updatePossibleNetworks.add(state.uploadNetworkIndex,network)
            state.copy(
                uploadNetwork = null,
                possibleNetworks = updatePossibleNetworks
            )
        }
        Timber.d("delete possibleNetworks : ${_networkUiState.value.possibleNetworks.map { it.network }}")
    }

    fun uploadKeypair(keypair: KeypairData, index : Int){
        _keypairUiState.update { state ->
            state.copy(
                uploadKeypair = keypair,
                uploadKeypairIndex = index,
                possibleKeypairs = state.possibleKeypairs.filterIndexed { idx, _->  idx != index }
            )
        }
        Timber.d("index : $index")
        Timber.d("upload possibleKeypairs : ${_keypairUiState.value.possibleKeypairs.map { it.name }}")
    }
    fun updateKeypair(keypair: KeypairData, index : Int){
        val temp = _keypairUiState.value.uploadKeypair!!
        _keypairUiState.update { state ->
            val updatePossibleKeypairs = state.possibleKeypairs.toMutableList()
            updatePossibleKeypairs.add(state.uploadKeypairIndex,temp)
            state.copy(
                uploadKeypair = keypair,
                uploadKeypairIndex = if(index>=state.uploadKeypairIndex) index+1 else index,
                possibleKeypairs = updatePossibleKeypairs.filterNot { it == keypair }
            )
        }
    }
    fun deleteKeypair(keypair: KeypairData) {
        _keypairUiState.update { state ->
            val updatePossibleKeypairs = state.possibleKeypairs.toMutableList()
            updatePossibleKeypairs.add(state.uploadKeypairIndex,keypair)
            state.copy(
                uploadKeypair = null,
                possibleKeypairs = updatePossibleKeypairs
            )
        }
        Timber.d("delete possibleKeypairs : ${_keypairUiState.value.possibleKeypairs.map { it.name }}")
    }

    fun updateDetailsUiState(detailUiState: InstanceCreateDetailUiState){
        _detailUiState.update { detailUiState}
    }

    fun updateSourceUiState(sourceUiState: InstanceCreateSourceUiState){
        _sourceUiState.update { sourceUiState }
    }

    fun initializeMessage(){
        _createInstanceDialogState.update {
            it.copy(message = "")
        }
    }

//    fun updateCreateInstanceDialogUiState(state: CreateInstanceDialogState) {
//        _createInstanceDialogUiState.update {it}
//    }

    private fun getAllFlavorData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllFlavorData()
                .onSuccess { it ->
                    if (it != null) {
                        _flavorUiState.update { state ->
                            state.copy(possibleFlavors = it)
                        }
                    }else{
                       //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.e("message :${it.message} , code :${it.code}")
                }
        }
    }

    private fun getAllKeypairData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllKeypairData()
                .onSuccess { it ->
                    if (it != null) {
                        _keypairUiState.update { state ->
                            state.copy(possibleKeypairs = it)
                        }
                    }else{
                        //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.e("message :${it.message} , code :${it.code}")
                }
        }
    }

    private fun getAllNetworkData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllNetworkData()
                .onSuccess { it ->
                    if (it != null) {
                        _networkUiState.update { state ->
                            state.copy(possibleNetworks = it)
                        }
                    }else{
                        //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.e("message :${it.message} , code :${it.code}")
                }
        }
    }

    private fun getAllSourceData(){
        viewModelScope.launch {
            instanceCreateRepository.getAllSourceData()
                .onSuccess { it ->
                    if (it != null) {
                        _sourceUiState.update { state ->
                            state.copy(possibleSources = it)
                        }
                    }else{
                        //_instances.value = testInstanceDataList
                    }
                }.onFailure {
                    it as RetrofitFailureStateException
                    Timber.e("message :${it.message} , code :${it.code}")
                }
        }
    }
    private fun getInstanceData() {
        viewModelScope.launch {
            var instanceCount = 0
            dashboardRepository.getDashboardData()
                .onSuccess { it ->
                    instanceCount = it  ?.instanceCount ?: 0
                }.onFailure {
                    Timber.e(it.message)
                }
            _detailUiState.update { state ->
                state.copy(currentCount = instanceCount)
            }
        }
    }

}


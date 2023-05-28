package com.knu.cloud.screens.home.keypairs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.model.instanceCreate.KeypairData
import com.knu.cloud.model.keypair.KeypairCreateRequest
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.repository.home.keypair.KeypairRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class KeypairsUiState(
    val isLoading : Boolean = false,
    val keypairs : List<KeypairData> = emptyList(),
    val checkedKeypairIds : List<String> = emptyList(),
    val deleteComplete : Boolean = false,
    val deleteResult : List<Pair<String,Boolean>> = emptyList(),
    val showCreateKeyPairDialog : Boolean = false
)


@HiltViewModel
class KeypairsViewModel @Inject constructor (
    private val keypairRepository: KeypairRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(KeypairsUiState())
    val uiState: StateFlow<KeypairsUiState> = _uiState.asStateFlow()

    init {
        getAllKeypairs()
    }

    private fun getAllKeypairs() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            keypairRepository.getKeypairs()
                .onSuccess { keypairs ->
                    if (keypairs != null) {
                        Timber.d("keypairs : $keypairs")
                        _uiState.update { it.copy(keypairs = keypairs, isLoading = false) }
                    } else {
                        _uiState.update { it.copy(keypairs = emptyList(), isLoading = false) }
                    }
                }.onFailure {
                    _uiState.update { state ->
                        state.copy(keypairs = emptyList(), isLoading = false)
                    }
                    it as RetrofitFailureStateException
                    Timber.e("message :${it.message} , code :${it.code}")
                }
        }
    }

    fun createKeypair(keypairCreateRequest: KeypairCreateRequest) {
        viewModelScope.launch {
            keypairRepository.createKeypair(keypairCreateRequest)
                .onSuccess {
                    Timber.d("onSuccess KeypairCreateResponse : $it")
                    getAllKeypairs()
                }
                .onFailure {
                    Timber.d("onFailure : ${it.message}")
                }
            _uiState.update { state ->
                state.copy(showCreateKeyPairDialog = false)
            }
        }
    }

    fun deleteKeypairs() {
        viewModelScope.launch {
            val deleteSuccessList: MutableList<String> = mutableListOf()
            _uiState.value.checkedKeypairIds.forEach { keypairName ->
                keypairRepository.deleteKeypair(keypairName)
                    .onSuccess {
                        deleteSuccessList.add(keypairName)
                    }.onFailure {
                        it as RetrofitFailureStateException
                        Timber.e("message :${it.message} , code :${it.code}")
                    }
            }
            _uiState.update { state ->
                state.copy(
                    keypairs = state.keypairs.filterNot { it.name in deleteSuccessList },                              // 삭제 성공한  리스트에 없는 images
                    deleteResult = state.checkedKeypairIds.map { id ->
                        Pair(id, id in deleteSuccessList)
                    },
                    deleteComplete = true
                )
            }
        }
    }

    fun keypairCheck(keypairId: String) {
        if (keypairId !in _uiState.value.checkedKeypairIds) {
            _uiState.update { it.copy(checkedKeypairIds = it.checkedKeypairIds + keypairId) }
        }
        Timber.d("checkedKeypairIds : ${uiState.value.checkedKeypairIds}")
    }

    fun keypairUnCheck(keypairId: String) {
        _uiState.update { state ->
            state.copy(
                checkedKeypairIds = state.checkedKeypairIds.filterNot { it == keypairId }
            )
        }
        Timber.d("checkedKeypairIds : ${uiState.value.checkedKeypairIds}")
    }

    fun allKeypairsCheck(allChecked: Boolean) {
        if (allChecked) {
            _uiState.update { state ->
                state.copy(checkedKeypairIds = state.keypairs.map { it.name })
            }
        } else initializeCheckKeypairIds()
        Timber.d("checkedKeypairIds : ${uiState.value.checkedKeypairIds}")
    }

    fun closeDeleteResultDialog() {
        _uiState.update {
            it.copy(deleteComplete = false, checkedKeypairIds = emptyList())
        }
    }
    fun showCreateKeypairDialog(){
        _uiState.update {
            it.copy(showCreateKeyPairDialog = true)
        }
    }
    fun closeCreateKeypairDialog(){
        _uiState.update {
            it.copy(showCreateKeyPairDialog = false)
        }
    }

    private fun initializeCheckKeypairIds() {
        _uiState.update { state ->
            state.copy(checkedKeypairIds = emptyList())
        }
        Timber.d("checkedKeypairIds : ${uiState.value.checkedKeypairIds}")
    }
}
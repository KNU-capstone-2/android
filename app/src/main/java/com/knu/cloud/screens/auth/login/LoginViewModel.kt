package com.knu.cloud.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.network.SessionManager
import com.knu.cloud.repository.AuthRepository
import com.knu.cloud.utils.ToastStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import timber.log.Timber
import javax.inject.Inject

const val testEmail  ="test"
const val testPassword = "1234"

data class LoginUiState(
    val userName  : String ="",
    val password: String = "",
    val isLoading : Boolean = false,
    val navigateToHome : Boolean = false,
    val message : String = "",
    val toastStatus: ToastStatus = ToastStatus.INFO
)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState :StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(){
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            authRepository.login(
                uiState.value.userName, uiState.value.password
//            testEmail, testPassword
            ).onSuccess { msg ->
                sessionManager.setUserName(_uiState.value.userName)
                _uiState.update {
                    it.copy(
                        navigateToHome = true,
                        isLoading = false,
                        message = "로그인 성공",
                        toastStatus = ToastStatus.SUCCESS,
                    )
                }
                Timber.tag("login").d("Success loginMsg : $msg")
            }.onFailure { t ->
                _uiState.update {
                    it.copy(
                        message = "로그인 실패",
                        toastStatus = ToastStatus.ERROR,
                        isLoading = false
                    )
                }
                Timber.tag("login").d("Failure loginMsg : ${t.message}")
            }
        }
    }
    fun updateUserEmail(email: String) {
        _uiState.update {
            it.copy(userName = email)
        }
        Timber.tag("login").d("updateUserEmail $email")
    }

    fun updateUserPassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
        Timber.tag("login").d("updateUserPassword $password")
    }
    fun initializeMessage(){
        _uiState.update {
            it.copy(message = "")
        }
    }

}

package com.knu.cloud.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.R
import com.knu.cloud.network.SessionManager
import com.knu.cloud.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val testEmail  ="test"
const val testPassword = "1234"

data class LoginUiState(
    val email  : String ="",
    val password: String = "",
    val isLoading : Boolean = false,
    val navigateToHome : Boolean = false,
    val userMessage : Int? = null,
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
//                uiState.value.email, uiState.value.password
            testEmail, testPassword
            )
                .onSuccess { msg ->
                    _uiState.update {
                        it.copy(
                            userMessage = R.string.Login_success,
                            navigateToHome = true,
                            isLoading = false
                        )
                    }
                    Timber.tag("login").d("Success loginMsg : $msg")
                }.onFailure { t ->
                    _uiState.update {
                        it.copy(
                            userMessage = R.string.Login_failure,
                            isLoading = false
                        )
                    }
                    Timber.tag("login").d("Failure loginMsg : ${t.message}")
                }
        }
    }
    fun updateUserEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
        Timber.tag("login").d("updateUserEmail $email")
    }

    fun updateUserPassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
        Timber.tag("login").d("updateUserPassword $password")
    }

//    fun initializeLoginMsg(){
//        _loginMsg.value = ""
//    }
//    fun loginFinish(){
//        _isLoggedIn.value = false
//    }
//    fun getLoginFlow(): StateFlow<Boolean> {
//        return sessionManager.isLoggedIn
//    }

//    fun getLoginErrorFlow() :StateFlow<String>{
//        return sessionManager.errorMessage
//    }

}

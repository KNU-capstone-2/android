package com.knu.cloud.screens.auth.login

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val userEmail = MutableStateFlow("")
    private val userPassword = MutableStateFlow("")

    private val _loginState = MutableStateFlow<Boolean>(false)
    val loginState
        get() = _loginState.asStateFlow()

    private val _loginError = MutableStateFlow<String>("")
    val loginError
        get() = _loginError.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.isSignIn.collect { isSignIn ->
                _loginState.value = isSignIn
            }
        }
        viewModelScope.launch {
            authRepository.signInError.collect { errorMsg ->
                _loginError.value = errorMsg
            }
        }
    }

    fun setUserEmail(email: String) {
        userEmail.value = email
        Timber.tag("Login_UserEmail").d(userEmail.value)
    }

    fun setUserPassword(password: String) {
        userPassword.value = password
        Timber.tag("Login_userPassword").d(userPassword.value)
    }

    fun login() {
        //authRepository.login(userEmail = userEmail.value, userPassword = userPassword.value)
        _loginError.value = "등록되지 않았거나 잘못된 정보 입니다."
    }

}

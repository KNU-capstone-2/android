package com.knu.cloud.screens.auth.login

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.di.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {
    private val userEmail = MutableStateFlow("")
    private val userPassword = MutableStateFlow("")

    var isLoggedIn = mutableStateOf(false)
    fun setUserEmail(email: String) {
        userEmail.value = email
        Timber.tag("Login_UserEmail").d(userEmail.value)
    }

    fun setUserPassword(password: String) {
        userPassword.value = password
        Timber.tag("Login_userPassword").d(userPassword.value)
    }

    fun getLoginFlow(): StateFlow<Boolean> {
        return sessionManager.isLoggedIn
    }

//    fun getLoginErrorFlow() :StateFlow<String>{
//        return sessionManager.errorMessage
//    }

}
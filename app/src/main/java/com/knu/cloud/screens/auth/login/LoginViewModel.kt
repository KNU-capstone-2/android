package com.knu.cloud.screens.auth.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): ViewModel() {
    private val userEmail = MutableStateFlow("")
    private val userPassword = MutableStateFlow("")

    fun setUserEmail(email: String) {
        userEmail.value = email
        Timber.tag("Login_UserEmail").d(userEmail.value)
    }

    fun setUserPassword(password: String) {
        userPassword.value = password
        Timber.tag("Login_userPassword").d(userPassword.value)
    }


}
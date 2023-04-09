package com.knu.cloud.screens.auth.signup

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.repository.AuthRepository
import com.knu.cloud.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val userEmail = MutableStateFlow("")
    private val _userEmailError = MutableStateFlow(false)

    val userEmailError
        get() = _userEmailError.asStateFlow()

    private val _userEmailErrorState = MutableStateFlow("")
    val userEmailErrorState
        get() = _userEmailErrorState.asStateFlow()

    private val userPassword = MutableStateFlow("")
    private val _userPasswordError = MutableStateFlow(false)
    val userPasswordError
        get() = _userPasswordError.asStateFlow()

    private val _userPasswordErrorState = MutableStateFlow("")
    val userPasswordErrorState
        get() = _userPasswordErrorState.asStateFlow()

    private val userPasswordCheck = MutableStateFlow("")


    fun signUp(){
        viewModelScope.launch{
            authRepository.signUp("test","user","1234")
        }
//        authRepository.signUp( "test","user","1234"
//            email = userEmail.value,
//            username = "test",
//            password = userPassword.value
//        )
    }

    fun setUserEmail(email: String) {
        userEmail.value = email
        Timber.tag("SignUp_UserEmail").d(userEmail.value)
    }

    fun setUserPassword(password: String) {
        userPassword.value = password
        Timber.tag("SignUp_userPassword").d(userPassword.value)
    }

    fun setUserPasswordCheck(pwCheck: String) {
        userPasswordCheck.value = pwCheck
        Timber.d("SignUp_userPasswordCheck", userPasswordCheck.value)
    }

    private fun isEmailEmpty(): Boolean {
        val condition = userEmail.value.isEmpty()
        return getResultOfEmailCondition(condition, SignUpErrorState.EMAIL_EMPTY)
    }

    private fun isEmailPattern(): Boolean {
        val condition = !PatternsCompat.EMAIL_ADDRESS.matcher(userEmail.value).matches()
        return getResultOfEmailCondition(condition, SignUpErrorState.EMAIL_PATTERN_NOT_SATISFIED)
    }

    private fun isPasswordEmpty(): Boolean {
        val condition = userPassword.value.isEmpty()
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_EMPTY)
    }

    private fun isPasswordLength(minLen: Int = 6, maxLen: Int = 20): Boolean {
        val length = userPassword.value.length
        val condition = length < minLen || length > maxLen
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_LENGTH_NOT_SATISFIED)
    }

    private fun isPasswordValidation(): Boolean {
        val reg = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}")
        val condition = !userPassword.value.matches(reg)
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_VALIDATION_NOT_SATISFIED)
    }

    private fun isPasswordSame(): Boolean {
        val condition = userPassword.value != userPasswordCheck.value
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_NOT_SAME)
    }

    // 조건을 만족하면 true, 아니면 false 반환
    private fun getResultOfEmailCondition(condition: Boolean, errorState: String): Boolean {
        _userEmailError.value = condition
        _userEmailErrorState.value = if (condition) errorState else ""
        return condition
    }

    // 조건을 만족하면 true, 아니면 false 반환
    private fun getResultOfPwCondition(condition: Boolean, errorState: String): Boolean {
        _userPasswordError.value = condition
        _userPasswordErrorState.value = if (condition) errorState else ""
        return condition
    }

    fun passAllConditions(): Boolean {
        if (isEmailEmpty()) return false
        if (isEmailPattern()) return false
        if (isPasswordEmpty()) return false
        if (isPasswordLength()) return false
        if (isPasswordValidation()) return false
        if (isPasswordSame()) return false
        return true
    }
}
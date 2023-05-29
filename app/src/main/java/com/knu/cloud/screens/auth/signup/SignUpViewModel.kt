package com.knu.cloud.screens.auth.signup

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.cloud.network.RetrofitFailureStateException
import com.knu.cloud.repository.AuthRepository
import com.knu.cloud.repository.AuthRepositoryImpl
import com.knu.cloud.screens.auth.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val testEmail = "hihi"
const val testUsername = "hihi"
const val testPassword = "1234"


data class SignUpState(
    val navigateToLogin : Boolean = false,
    val userNickName : String ="",
    val userEmail: String = "",
    val userEmailError: Boolean = false,
    val userEmailErrorState: String = "",
    val userPassword: String = "",
    val userPasswordError: Boolean = false,
    val userPasswordErrorState: String = "",
    val userPasswordCheck: String = "",
    val message : String = ""
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(SignUpState())
    val uiState : StateFlow<SignUpState> = _uiState.asStateFlow()

    fun signUp() {
        viewModelScope.launch {
            authRepository.signUp(
                email = testEmail,
                username = testUsername,
                password = testPassword
//                email = _uiState.value.userEmail,
//                username = _uiState.value.userNickName,
//                password = _uiState.value.userPassword
            ).onSuccess {
                Timber.d(it)
                _uiState.update { state ->
                    state.copy(
                        navigateToLogin = true,
                        message = "회원가입 성공"
                    )
                }
            }.onFailure {
                it as RetrofitFailureStateException
                Timber.d(it)
                _uiState.update { state ->
                    state.copy(message = it.message.toString())
                }
            }
        }
    }
    fun setUserNickName(nickName: String) {
        _uiState.update {
            it.copy(userNickName = nickName)
        }
        Timber.tag("SignUp_UserNickName").d(_uiState.value.userNickName)
    }

    fun setUserEmail(email: String) {
        _uiState.update {
            it.copy(userEmail = email)
        }
        Timber.tag("SignUp_UserEmail").d(_uiState.value.userEmail)
    }

    fun setUserPassword(password: String) {
        _uiState.update {
            it.copy(userPassword = password)
        }
        Timber.tag("SignUp_userPassword").d(_uiState.value.userPassword)
    }

    fun setUserPasswordCheck(pwCheck: String) {
        _uiState.update {
            it.copy(userPasswordCheck = pwCheck)
        }
        Timber.tag("SignUp_userPasswordCheck").d(_uiState.value.userPasswordCheck)
    }

    private fun isEmailEmpty(): Boolean {
        val condition = _uiState.value.userEmail.isEmpty()
        return getResultOfEmailCondition(condition, SignUpErrorState.EMAIL_EMPTY)
    }

    private fun isEmailPattern(): Boolean {
        val condition = !PatternsCompat.EMAIL_ADDRESS.matcher(_uiState.value.userEmail).matches()
        return getResultOfEmailCondition(condition, SignUpErrorState.EMAIL_PATTERN_NOT_SATISFIED)
    }

    private fun isPasswordEmpty(): Boolean {
        val condition = _uiState.value.userPassword.isEmpty()
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_EMPTY)
    }

    private fun isPasswordLength(minLen: Int = 6, maxLen: Int = 20): Boolean {
        val length = _uiState.value.userPassword.length
        val condition = length < minLen || length > maxLen
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_LENGTH_NOT_SATISFIED)
    }

    private fun isPasswordValidation(): Boolean {
        val reg = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}")
        val condition = !_uiState.value.userPassword.matches(reg)
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_VALIDATION_NOT_SATISFIED)
    }

    private fun isPasswordSame(): Boolean {
        val condition = _uiState.value.userPassword != _uiState.value.userPasswordCheck
        return getResultOfPwCondition(condition, SignUpErrorState.PASSWORD_NOT_SAME)
    }

    // 조건을 만족하면 true, 아니면 false 반환
    private fun getResultOfEmailCondition(condition: Boolean, errorState: String): Boolean {
        _uiState.update {
            it.copy(
                userEmailError = condition,
                userEmailErrorState = if(condition) errorState else ""
            )
        }
        return condition
    }

    // 조건을 만족하면 true, 아니면 false 반환
    private fun getResultOfPwCondition(condition: Boolean, errorState: String): Boolean {
        _uiState.update {
            it.copy(
                userPasswordError = condition,
                userPasswordErrorState = if(condition) errorState else ""
            )
        }
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
package com.knu.cloud.di

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager {
    private val _sessionId = MutableStateFlow("")
//    private val _isLoggedIn = MutableStateFlow(false)
////    private val _errorMessage = MutableStateFlow("")                  // 세션 만료에 대한 처리는 어디서할지 조금 더 고민해봐야함. 일단은 AuthResponse에서 처리하는걸로
//
//    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
////    val errorMessage :StateFlow<String> = _errorMessage.asStateFlow()
    val sessionId : StateFlow<String> = _sessionId.asStateFlow()
//
//    fun login(){
//        _isLoggedIn.value = true
//    }
//    fun logout() {
//        _isLoggedIn.value = false
//        _sessionId.value = ""
//    }

    fun setSessionId(sessionId : String){
        _sessionId.value = sessionId
    }

//    fun setErrorMessage(msg :String){
//        _errorMessage.value = msg
//    }
}
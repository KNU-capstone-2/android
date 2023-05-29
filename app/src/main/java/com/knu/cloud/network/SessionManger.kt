package com.knu.cloud.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

data class AuthState(
    val sessionId: String = "",
    val isLoggedIn : Boolean = false,
    val message : String = "",
)

class SessionManager {
    private val _authState = MutableStateFlow(AuthState())
    val authState : StateFlow<AuthState> = _authState.asStateFlow()

    fun login(sessionId: String){
        _authState.update { state ->
            state.copy(sessionId = sessionId, isLoggedIn = true)
        }
        Timber.d("sessionId :${_authState.value.sessionId}, isLoggedIn ${_authState.value.isLoggedIn}")
    }
    fun logout() {
        _authState.update { state ->
            state.copy(sessionId = "", isLoggedIn = false)
        }
        Timber.d("sessionId :${_authState.value.sessionId}, isLoggedIn ${_authState.value.isLoggedIn}")
    }

    fun setSessionId(sessionId : String){
        _authState.update { state ->
            state.copy(sessionId = sessionId)
        }
        Timber.d("sessionId :${_authState.value.sessionId}")
    }

    fun setMessage(message :String){
        _authState.update { state ->
            state.copy(message = message)
        }
        Timber.d("message :${_authState.value.message}")
    }
}
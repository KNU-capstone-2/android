package com.knu.cloud.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.knu.cloud.data.auth.AuthLocalDataSource.PreferenceKeys.TOKEN
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.Token
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    private val dataStore : DataStore<Preferences>
) {
    private object PreferenceKeys{
        val TOKEN = stringPreferencesKey("token")
    }

    // TODO : 리턴 타입 일치시켜야할듯 Datastore은 반환 값이 기본 Flow형태라
    suspend fun getTokenFlow(loginRequest: LoginRequest): Flow<Token> {
        TODO("Not yet implemented")
    }

    suspend fun saveToken(token: Token) {
        if(token.sessionId.isNotEmpty()){
            dataStore.edit {prefs ->
                prefs[TOKEN] = token.sessionId
            }
        }
    }
}
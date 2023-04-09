package com.knu.cloud.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.knu.cloud.data.auth.AuthLocalDataSource.PreferenceKeys.TOKEN
import com.knu.cloud.model.auth.LoginRequest
import com.knu.cloud.model.auth.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    private val dataStore : DataStore<Preferences>
) {
    private object PreferenceKeys{
        val TOKEN = stringPreferencesKey("token")
    }


    // TODO : 리턴 타입 일치시켜야할듯 Datastore은 반환 값이 기본 Flow형태라
    suspend fun getTokenFlow(): Flow<String> {
        return dataStore.data
            .catch {exception ->
                if(exception is IOException){
                    exception.printStackTrace()
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }
            .map { prefs->
                prefs.asMap().values.toString()
            }
    }

    suspend fun saveToken(token: String) {
        if(token.isNotEmpty()){
            dataStore.edit {prefs ->
                prefs[TOKEN] = token
            }
        }
    }
}
package com.knu.cloud.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferenceDataStore(@ApplicationContext appContext : Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            //  this.applicationContext.fileDir+datastore/name에  File 객체 생성
            produceFile = {appContext.preferencesDataStoreFile("user_preferences")}
        )
    }
}
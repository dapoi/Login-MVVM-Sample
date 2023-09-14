package com.dapascript.loginsamplemvvm.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val USER_TOKEN = stringPreferencesKey("user_token")
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class DataStorePref @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    val userToken = dataStore.data.map { preferences ->
        preferences[USER_TOKEN] ?: ""
    }
}
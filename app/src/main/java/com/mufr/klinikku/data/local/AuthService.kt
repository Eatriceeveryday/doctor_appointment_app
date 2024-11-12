package com.mufr.klinikku.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.mufr.klinikku.shared.Constant
import kotlinx.coroutines.flow.first

class AuthService(private val dataStore: DataStore<Preferences>) {
    suspend fun saveAuthToken(token: String){
        dataStore.edit { pref ->
            pref[Constant.AUTH_KEY] = token
        }
    }

    suspend fun getAuthToken(): String{
        val pref = dataStore.data.first()
        return pref[Constant.AUTH_KEY] ?: ""
    }

}
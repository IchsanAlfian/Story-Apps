package com.ichsanalfian.mystoryapp.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun isUserLogin(state : Boolean){
        if(state){
            dataStore.edit { preferences ->
                preferences[STATE_KEY] = true
            }
        }else{
            dataStore.edit { preferences ->
                preferences[STATE_KEY] = false
            }
        }
    }
    suspend fun userLogin() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun userLogout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
        }
    }
    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            val name = preferences[NAME_KEY] ?: ""
            val state = preferences[STATE_KEY] ?: false
            val token = preferences[TOKEN_KEY] ?: ""
            UserModel(name, state, token)
        }
    }
    suspend fun saveUser(user: UserModel) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[NAME_KEY] = user.name
                preferences[STATE_KEY] = user.isLogin
                preferences[TOKEN_KEY] = user.token
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val STATE_KEY = booleanPreferencesKey("state")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME_KEY = stringPreferencesKey("name")
//        private val EMAIL_KEY = stringPreferencesKey("email")
//        private val PASSWORD_KEY = stringPreferencesKey("password")
        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
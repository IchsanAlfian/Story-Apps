package com.ichsanalfian.mystoryapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ichsanalfian.mystoryapp.api.ApiConfig
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.remote.StoryRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(preferences, apiService)
    }
}
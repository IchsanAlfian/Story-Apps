package com.ichsanalfian.mystoryapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.remote.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val pref: StoryRepository) : ViewModel() {
//    fun getUser(): LiveData<UserModel> {
//        return pref.getUser().asLiveData()
//    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}
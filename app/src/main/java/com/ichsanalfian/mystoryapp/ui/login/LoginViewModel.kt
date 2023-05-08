package com.ichsanalfian.mystoryapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.remote.StoryRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: StoryRepository) : ViewModel() {
//    fun getUser(): LiveData<UserModel> {
//        return pref.getUser().asLiveData()
//    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}
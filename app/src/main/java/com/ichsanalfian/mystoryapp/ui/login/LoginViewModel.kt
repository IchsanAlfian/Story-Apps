package com.ichsanalfian.mystoryapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.remote.StoryRepository
import com.ichsanalfian.mystoryapp.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: StoryRepository) : ViewModel() {
    //    fun getUser(): LiveData<UserModel> {
//        return pref.getUser().asLiveData()
//    }
    val login : LiveData<LoginResponse> = pref.login
    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            pref.postLogin(email, password)
        }
    }

    fun saveSession(session: UserModel) {
        viewModelScope.launch {
            pref.saveSession(session)
        }
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}
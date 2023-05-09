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

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    //    fun getUser(): LiveData<UserModel> {
//        return pref.getUser().asLiveData()
//    }
    val login : LiveData<LoginResponse> = repository.login
    fun loginRequest(email: String, password: String) {
        viewModelScope.launch {
            repository.loginRequest(email, password)
        }
    }
    fun userLogin() {
        viewModelScope.launch {
            repository.userLogin()
        }
    }
    fun saveUser(uModel: UserModel) {
        viewModelScope.launch {
            repository.saveUser(uModel)
        }
    }

}
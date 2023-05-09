package com.ichsanalfian.mystoryapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.remote.StoryRepository

import com.ichsanalfian.mystoryapp.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {
    val register : LiveData<RegisterResponse> = repository.register
    fun postRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.registerRequest(name, email, password)
        }
    }
}
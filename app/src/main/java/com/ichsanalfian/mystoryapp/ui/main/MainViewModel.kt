package com.ichsanalfian.mystoryapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.remote.StoryRepository

class MainViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return repository.getUser()
    }

}
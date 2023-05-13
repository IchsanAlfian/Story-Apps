package com.ichsanalfian.mystoryapp.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.remote.StoryRepository
import com.ichsanalfian.mystoryapp.response.StoryResponse
import kotlinx.coroutines.launch

class StoryViewModel(private val repository: StoryRepository) : ViewModel() {
    val story: LiveData<StoryResponse> = repository.story
    val isLoading: LiveData<Boolean> = repository.isLoading
    fun getAllStory(token: String) {
        viewModelScope.launch {
            repository.getAllStory(token)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return repository.getUser()
    }

    fun userLogout() {
        viewModelScope.launch {
            repository.userLogout()
        }
    }
}
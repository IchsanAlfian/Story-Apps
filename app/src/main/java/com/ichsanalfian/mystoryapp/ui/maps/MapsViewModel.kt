package com.ichsanalfian.mystoryapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.remote.StoryRepository
import com.ichsanalfian.mystoryapp.response.StoryResponse
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    val story: LiveData<StoryResponse> = repository.story
    val isLoading: LiveData<Boolean> = repository.isLoading
    fun getLocation(token: String) {
        viewModelScope.launch {
            repository.getLocationStory(token, 1)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return repository.getUser()
    }
}
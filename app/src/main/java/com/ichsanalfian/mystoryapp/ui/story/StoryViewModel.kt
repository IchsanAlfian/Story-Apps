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
    fun getListStories(token: String) {
        viewModelScope.launch {
            repository.getListStories(token)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
package com.ichsanalfian.mystoryapp.ui.addStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.remote.StoryRepository
import com.ichsanalfian.mystoryapp.response.AddStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val upload: LiveData<AddStoryResponse> = repository.upload
    fun uploadStoryRequest(image: MultipartBody.Part, desc: RequestBody, token: String) {
        viewModelScope.launch {
            repository.uploadStoryRequest(image, desc, token)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return repository.getUser()
    }
}
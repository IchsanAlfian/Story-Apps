package com.ichsanalfian.mystoryapp.remote

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.ichsanalfian.mystoryapp.R
import com.ichsanalfian.mystoryapp.api.ApiService
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.response.AddStoryResponse
import com.ichsanalfian.mystoryapp.response.LoginResponse
import com.ichsanalfian.mystoryapp.response.RegisterResponse
import com.ichsanalfian.mystoryapp.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository private constructor(
    private val userPref: UserPreference,
    private val apiService: ApiService
) {
    private val _register = MutableLiveData<RegisterResponse>()
    val register: LiveData<RegisterResponse> = _register

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    private val _story = MutableLiveData<StoryResponse>()
    val story: LiveData<StoryResponse> = _story

    private val _upload = MutableLiveData<AddStoryResponse>()
    val upload: LiveData<AddStoryResponse> = _upload

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun userLogout() {
        userPref.isUserLogin(false)
    }

    suspend fun userLogin() {
        userPref.isUserLogin(true)
    }

    fun registerRequest(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = apiService.registerRequest(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _register.value = response.body()
                } else {
                    Log.e("StoryRepositoryRegister", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("StoryRepositoryRegister", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }

    fun loginRequest(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.loginRequest(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _login.value = response.body()
                } else {
                    Log.e("StoryRepositoryLogin", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("StoryRepositoryLogin", "onFailure: ${t.message.toString()}")

                t.printStackTrace()
            }
        })
    }

    fun uploadStoryRequest(image: MultipartBody.Part, desc: RequestBody, token: String) {
        _isLoading.value = true
        val client = apiService.addStory(image, desc, token)
        client.enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(
                call: Call<AddStoryResponse>,
                response: Response<AddStoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _upload.value = response.body()
                } else {
                    Log.e("StoryRepositoryUpload", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("StoryRepositoryUpload", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }

    fun getAllStory(token: String) {
        _isLoading.value = true
        val client = apiService.getAllStory(token)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _story.value = response.body()
                } else {
                    Log.e("StoryRepositoryStory", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("StoryRepositoryStory", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }

    suspend fun saveUser(uModel: UserModel) {
        userPref.saveUser(uModel)
    }

    fun getUser(): LiveData<UserModel> {
        return userPref.getUser().asLiveData()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            preferences: UserPreference,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(preferences, apiService)
            }.also { instance = it }
    }
}
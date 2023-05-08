package com.ichsanalfian.mystoryapp.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ichsanalfian.mystoryapp.api.ApiService
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository private constructor(
    private val userPref: UserPreference,
    private val apiService: ApiService
) {
    private val _register = MutableLiveData<RegisterResponse>()
    val register : LiveData<RegisterResponse> = _register

    fun postRegister(name : String, email: String, password: String){
        val client = apiService.postRegister(name, email, password)

        client.enqueue(object : Callback<RegisterResponse>
        {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    _register.value = response.body()
                } else {
                    Log.e("StoryRepositoryRegister", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                _isLoading.value = false
                Log.e("StoryRepositoryRegister", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }
    suspend fun login() {
        userPref.login()
    }
    suspend fun logout() {
        userPref.logout()
    }
    companion object {
        private const val TAG = "StoryRepository"

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
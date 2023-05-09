package com.ichsanalfian.mystoryapp.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.ichsanalfian.mystoryapp.api.ApiService
import com.ichsanalfian.mystoryapp.model.UserModel
import com.ichsanalfian.mystoryapp.model.UserPreference
import com.ichsanalfian.mystoryapp.response.LoginResponse
import com.ichsanalfian.mystoryapp.response.RegisterResponse
import com.ichsanalfian.mystoryapp.response.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class StoryRepository private constructor(private val userPref: UserPreference, private val apiService: ApiService) {
    private val _register = MutableLiveData<RegisterResponse>()
    val register : LiveData<RegisterResponse> = _register

    private val _login = MutableLiveData<LoginResponse>()
    val login : LiveData<LoginResponse> = _login

    private val _story = MutableLiveData<StoryResponse>()
    val story : LiveData<StoryResponse> = _story

    suspend fun userLogout() {
//        userPref.isUserLogin(false)
        userPref.userLogout()
    }
    suspend fun userLogin() {
//        userPref.isUserLogin(true)
        userPref.userLogin()
    }

    fun registerRequest(name : String, email: String, password: String){
        val client = apiService.registerRequest(name, email, password)

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
    fun loginRequest(email: String, password: String){
        val client = apiService.loginRequest(email, password)

        client.enqueue(object : Callback<LoginResponse>
        {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _login.value = response.body()
                } else {
                    Log.e("StoryRepositoryLogin", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                _isLoading.value = false
                Log.e("StoryRepositoryLogin", "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }
    fun getAllStory(token : String){
        val client = apiService.getAllStory(token)

        client.enqueue(object : Callback<StoryResponse>
        {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                if (response.isSuccessful) {
                    _story.value = response.body()
                } else {
                    Log.e("StoryRepositoryStory", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                _isLoading.value = false
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
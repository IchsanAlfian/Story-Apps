package com.ichsanalfian.mystoryapp.api

import com.ichsanalfian.mystoryapp.response.AddStoryResponse
import com.ichsanalfian.mystoryapp.response.LoginResponse
import com.ichsanalfian.mystoryapp.response.RegisterResponse
import com.ichsanalfian.mystoryapp.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("stories")
    suspend fun getAllStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ):StoryResponse
    @GET("stories")
    fun getStoriesLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ):Call<StoryResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginRequest(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun registerRequest(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @Multipart
    @POST("stories")
    fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String
    ): Call<AddStoryResponse>


}
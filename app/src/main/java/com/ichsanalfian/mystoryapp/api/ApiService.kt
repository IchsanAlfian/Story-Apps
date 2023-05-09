package com.ichsanalfian.mystoryapp.api

import com.ichsanalfian.mystoryapp.response.LoginResponse
import com.ichsanalfian.mystoryapp.response.RegisterResponse
import com.ichsanalfian.mystoryapp.response.StoryResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>
    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
    @GET("stories")
    fun getListStories(
        @Header("Authorization") token: String
    ):Call<StoryResponse>
}
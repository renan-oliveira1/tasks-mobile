package com.example.taskmobile.data.services

import com.example.taskmobile.data.model.LoginModel
import com.example.taskmobile.data.model.TokenModel
import com.example.taskmobile.data.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("user/login")
    fun login(
        @Body loginModel: LoginModel
    ): Call<TokenModel>

    @POST("user/register")
    fun register(@Body user: UserModel): Call<TokenModel>

    @GET("users")
    fun getUsers(): Call<List<UserModel>>


}
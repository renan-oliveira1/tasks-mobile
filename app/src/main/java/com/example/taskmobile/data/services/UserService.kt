package com.example.taskmobile.data.services

import com.example.taskmobile.data.model.LoginModel
import com.example.taskmobile.data.model.TokenModel
import com.example.taskmobile.data.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("user/login")
    fun login(
        @Body loginModel: LoginModel
    ): Call<TokenModel>

    @POST("user/register")
    fun register(@Body user: UserModel): Call<TokenModel>


}
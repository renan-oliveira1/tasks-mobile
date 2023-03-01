package com.example.taskmobile.data.repositories

import android.util.Log
import com.example.taskmobile.data.model.LoginModel
import com.example.taskmobile.data.model.TokenModel
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.data.services.UserService
import com.google.gson.annotations.SerializedName
import retrofit2.await

class UserRepository(private val service: UserService) {

    suspend fun login(loginModel: LoginModel): TokenModel{
        return service.login(loginModel).await()
    }

    suspend fun register(user: UserModel): TokenModel{
        return service.register(user).await()
    }

    suspend fun get(): List<UserModel>{
        return service.getUsers().await()
    }

}
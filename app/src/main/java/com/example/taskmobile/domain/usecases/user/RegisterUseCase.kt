package com.example.taskmobile.domain.usecases.user

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.TokenModel
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.data.repositories.UserRepository
import com.example.taskmobile.data.services.UserService

class RegisterUseCase  {

    private val repository: UserRepository = UserRepository(
        RetrofitClient.getRetrofitInstance().create(UserService::class.java)
    )

    suspend fun execute(user: UserModel): Resource<TokenModel> {

        return try {
            val token = repository.register(user)
            RetrofitClient.addHeader(token.token)

            Resource.Success(token)
        }catch (e: Exception){
            Resource.Error("Error to register!!")
        }

    }
}
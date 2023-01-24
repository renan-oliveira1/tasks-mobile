package com.example.taskmobile.domain.usecases.user

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.data.repositories.UserRepository
import com.example.taskmobile.data.services.UserService

class RegisterUseCase  {

    private val repository: UserRepository = UserRepository(
        RetrofitClient.getRetrofitInstance().create(UserService::class.java)
    )

    suspend fun execute(user: UserModel): Boolean {

        return try {
            val token = repository.register(user)
            RetrofitClient.addHeader(token.token)

            true
        }catch (e: Exception){
            false
        }

    }
}
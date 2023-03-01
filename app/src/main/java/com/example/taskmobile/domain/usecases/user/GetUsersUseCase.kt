package com.example.taskmobile.domain.usecases.user

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.LoginModel
import com.example.taskmobile.data.model.TokenModel
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.data.repositories.UserRepository

class GetUsersUseCase(private val repository: UserRepository) {

    suspend fun execute(): Resource<List<UserModel>> {

        return try {
            val users = repository.get()

            Resource.Success(users)

        }catch (e: Exception){
            Resource.Error("Error to load users!!")
        }

    }
}
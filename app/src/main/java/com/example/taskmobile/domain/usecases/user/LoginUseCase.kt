package com.example.taskmobile.domain.usecases.user

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.LoginModel
import com.example.taskmobile.data.repositories.UserRepository
import com.example.taskmobile.data.services.UserService

class LoginUseCase {

    private val repository: UserRepository = UserRepository(
        RetrofitClient.getRetrofitInstance().create(UserService::class.java)
    )

    suspend fun execute(email: String, password: String): Boolean {

        return try {
            val loginModel = LoginModel(email, password)

            val tokenModel = repository.login(loginModel)
            RetrofitClient.addHeader(tokenModel.token)

            true
        }catch (e: Exception){
            e.message
            false
        }

    }

}
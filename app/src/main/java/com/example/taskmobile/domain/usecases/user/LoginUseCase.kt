package com.example.taskmobile.domain.usecases.user

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.LoginModel
import com.example.taskmobile.data.model.TokenModel
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.data.repositories.UserRepository
import com.example.taskmobile.data.services.UserService

class LoginUseCase {

    private val repository: UserRepository = UserRepository(
        RetrofitClient.getRetrofitInstance().create(UserService::class.java)
    )

    suspend fun execute(email: String, password: String): Resource<TokenModel> {

        return try {
            val loginModel = LoginModel(email, password)

            val tokenModel = repository.login(loginModel)
            if(tokenModel == null){
                Resource.Error("Invalid email or password!!", null)
            }

            RetrofitClient.addHeader(tokenModel.token)
            Resource.Success(tokenModel)

        }catch (e: Exception){
            Resource.Error("Error to login!!")
        }

    }

}
package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.core.ResponseApi
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService

class CreateTaskUseCase(private val repository: TaskRepository) {

    suspend fun execute(task: Task): ResponseApi{
        return try {
            repository.create(task)

            ResponseApi.LOADED
        }catch (e: Exception){
            ResponseApi.ERROR
        }
    }
}
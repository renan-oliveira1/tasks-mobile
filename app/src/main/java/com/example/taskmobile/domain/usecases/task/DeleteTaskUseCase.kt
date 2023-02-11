package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.core.ResponseApi
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService

class DeleteTaskUseCase(private val repository: TaskRepository) {


    suspend fun execute(id: String): Task{
        return try {
            repository.delete(id)
        }catch (e: Exception){
            throw Exception(e.message)
        }
    }

}
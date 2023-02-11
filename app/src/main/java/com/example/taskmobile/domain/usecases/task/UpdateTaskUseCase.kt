package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService

class UpdateTaskUseCase(private val repository: TaskRepository){


    suspend fun execute(task: Task): Task {
        return try {
            repository.update(task)
        }catch (e: Exception){
            throw Exception(e)
        }
    }
}
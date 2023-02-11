package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.model.UpdateTaskStatusModel
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService

class UpdateStatusTaskUseCase(private val repository: TaskRepository){

    suspend fun execute(updateTaskStatusModel: UpdateTaskStatusModel): Task {
        return try {
            repository.updateStatus(updateTaskStatusModel)
        }catch (e: Exception){
            throw Exception(e)
        }
    }
}
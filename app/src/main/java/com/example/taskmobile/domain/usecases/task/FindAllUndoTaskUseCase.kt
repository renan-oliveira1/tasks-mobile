package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService

class FindAllUndoTaskUseCase(private val repository: TaskRepository){


    suspend fun execute(): List<Task>{
        return try {
            val tasks = repository.findAll()

            return tasks.filter { it.complete == false }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}
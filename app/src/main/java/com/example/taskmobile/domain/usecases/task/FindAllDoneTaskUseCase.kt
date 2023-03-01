package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository

class FindAllDoneTaskUseCase(private val repository: TaskRepository) {
    suspend fun execute(): List<Task>{
        return try {
            val tasks = repository.findAll()

            return tasks.filter { it.complete == true }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}
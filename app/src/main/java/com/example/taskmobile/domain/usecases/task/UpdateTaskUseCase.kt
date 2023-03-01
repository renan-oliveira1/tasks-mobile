package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService

class UpdateTaskUseCase(private val repository: TaskRepository){


    suspend fun execute(task: Task): Resource<Task> {
        return try {
            if(task.id == null)
                Resource.Error("Task not found!!", null)

            val apiTask = repository.findOne(task.id!!)

            if(apiTask == null)
                Resource.Error("Task not found!!", null)

            Resource.Success(repository.update(task))
        }catch (e: Exception){
            Resource.Error("Error to update task!!")
        }
    }
}
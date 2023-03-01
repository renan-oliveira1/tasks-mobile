package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService

class FindOneTaskUseCase(private val repository: TaskRepository) {

    suspend fun execute(id: String): Resource<Task>{
        return try {
            val task = repository.findOne(id)

            if(task == null)
                Resource.Error("Task not found!!", null)

            Resource.Success(task)
        }catch (e: Exception){
            Resource.Error("Error to find task!!", null)
        }
    }
}
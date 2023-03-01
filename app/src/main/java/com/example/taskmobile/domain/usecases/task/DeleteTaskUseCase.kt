package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {


    suspend fun execute(id: String): Resource<Task>{
        return try {
            val task = repository.findOne(id)
            if(task == null)
                Resource.Error("Task not exists!!", null)
            Resource.Success(repository.delete(id))
        }catch (e: Exception){
            Resource.Error("Error to delete task", null)
        }
    }

}
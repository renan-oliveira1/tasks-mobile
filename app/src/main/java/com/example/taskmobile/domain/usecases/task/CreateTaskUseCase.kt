package com.example.taskmobile.domain.usecases.task

import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.repositories.TaskRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

class CreateTaskUseCase(private val repository: TaskRepository) {

    suspend fun execute(task: Task): Resource<Task>{
        return try {
            val result = repository.create(task)
            if(result == null)
                Resource.Error("Error to send task!!", null)
            Resource.Success(result)
        }catch (e: Exception){
            Resource.Error("Error to send task to api!!", null)
        }
    }
}
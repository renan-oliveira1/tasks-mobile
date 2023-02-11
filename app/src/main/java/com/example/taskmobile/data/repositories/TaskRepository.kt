package com.example.taskmobile.data.repositories

import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.model.UpdateTaskStatusModel
import com.example.taskmobile.data.services.TaskService
import retrofit2.await

class TaskRepository(private val service: TaskService): Repository<Task, String> {
    override suspend fun create(task: Task): Task {
        return service.create(task).await()
    }

    override suspend fun findAll(): List<Task> {
        return service.findAll().await()
    }

    override suspend fun findOne(id: String): Task {
        return service.findOne(id).await()
    }

    override suspend fun update(task: Task): Task {
        return service.update(task).await()
    }

    suspend fun updateStatus(updateTaskStatusModel: UpdateTaskStatusModel): Task{
        return service.updateStatus(updateTaskStatusModel).await()
    }

    override suspend fun delete(id: String): Task {
        return service.delete(id).await()
    }
}
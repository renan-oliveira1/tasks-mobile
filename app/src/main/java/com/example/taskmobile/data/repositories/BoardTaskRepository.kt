package com.example.taskmobile.data.repositories

import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.data.model.UpdateStatusBoardTask
import com.example.taskmobile.data.services.BoardTaskService
import com.example.taskmobile.domain.repositories.Repository
import retrofit2.await

class BoardTaskRepository(private val service: BoardTaskService): Repository<BoardTask, String> {
    override suspend fun create(task: BoardTask): BoardTask {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(): List<BoardTask> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: String): BoardTask {
        return service.findOne(id).await()
    }

    override suspend fun update(task: BoardTask) : BoardTask{
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): BoardTask {
        return service.delete(id).await()
    }

    suspend fun updateStatus(updateStatusBoardTask: UpdateStatusBoardTask): BoardTask{
        return service.updateStatus(updateStatusBoardTask).await()
    }

    suspend fun createTask(task: SendBoardTask): BoardTask {
        return service.create(task).await()
    }

    suspend fun updateTask(task: SendBoardTask): BoardTask {
        return service.update(task).await()
    }
}
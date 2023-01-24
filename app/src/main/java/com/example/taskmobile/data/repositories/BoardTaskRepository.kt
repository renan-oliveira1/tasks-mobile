package com.example.taskmobile.data.repositories

import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.services.BoardTaskService
import retrofit2.await

class BoardTaskRepository(private val service: BoardTaskService): Repository<BoardTask, String> {
    override suspend fun create(task: BoardTask): BoardTask {
        return service.create(task).await()
    }

    override suspend fun findAll(): List<BoardTask> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: String): BoardTask {
        TODO("Not yet implemented")
    }

    override suspend fun update(task: BoardTask) : BoardTask{
        return service.update(task).await()
    }

    override suspend fun delete(id: String): BoardTask {
        return service.delete(id).await()
    }
}
package com.example.taskmobile.domain.usecases.board.task

import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.BoardTaskStatus
import com.example.taskmobile.data.model.UpdateStatusBoardTask
import com.example.taskmobile.data.repositories.BoardTaskRepository

class UpdateStatusBoardTaskUseCase(private val repository: BoardTaskRepository) {

    suspend fun execute(id: String, status: BoardTaskStatus): BoardTask {

        return try{
            val updateStatusBoardTask = UpdateStatusBoardTask(id, status)
            repository.updateStatus(updateStatusBoardTask)
        }catch (e: Exception){
            throw Exception(e)
        }

    }

}
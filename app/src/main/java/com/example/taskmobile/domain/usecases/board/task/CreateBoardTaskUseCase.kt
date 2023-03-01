package com.example.taskmobile.domain.usecases.board.task


import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.data.repositories.BoardTaskRepository

class CreateBoardTaskUseCase(private val repository: BoardTaskRepository) {

    suspend fun execute(task: SendBoardTask): Resource<BoardTask> {

        return try{
            Resource.Success(repository.createTask(task))
        }catch (e: Exception){
            Resource.Error("Error to create task!!", null)
            throw Exception(e)
        }

    }
}
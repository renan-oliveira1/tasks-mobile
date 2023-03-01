package com.example.taskmobile.domain.usecases.board.task

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.data.repositories.BoardTaskRepository

class UpdateBoardTaskUseCase(private val repository: BoardTaskRepository) {

    suspend fun execute(task: SendBoardTask): Resource<BoardTask> {

        return try{
            if(task.id == null){
                Resource.Error("Can't edit a task without your id!!", null)
            }
            Resource.Success(repository.createTask(task))
        }catch (e: Exception){
            Resource.Error("Error to edit task!!", null)
            throw Exception(e)
        }

    }
}
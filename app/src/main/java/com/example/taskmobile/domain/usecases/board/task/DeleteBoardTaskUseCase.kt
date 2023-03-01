package com.example.taskmobile.domain.usecases.board.task

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.repositories.BoardTaskRepository

class DeleteBoardTaskUseCase(private val repository: BoardTaskRepository) {
    suspend fun execute(id: String): Resource<BoardTask> {

        return try{
            val task = repository.findOne(id)
            if(task == null) Resource.Error("Task with id '$id' not found!!", task)

            Resource.Success(repository.delete(id))
        }catch (e: Exception){
            Resource.Error("Error delete task with id '$id'!!", null)
            throw Exception(e)
        }

    }
}
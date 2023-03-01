package com.example.taskmobile.domain.usecases.board.task

import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.repositories.BoardTaskRepository

class FindOneBoardTaskUseCase(private val repository: BoardTaskRepository) {

    suspend fun execute(id: String): BoardTask{
        return try{
            repository.findOne(id)
        }catch (e: Exception){
            throw Exception(e)
        }
    }
}
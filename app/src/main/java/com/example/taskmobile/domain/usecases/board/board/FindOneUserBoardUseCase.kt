package com.example.taskmobile.domain.usecases.board.board

import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.repositories.BoardRepository

class FindOneUserBoardUseCase(private val repository: BoardRepository) {

    suspend fun execute(id: String): Board{

        return try{
            repository.findOne(id)
        }catch (e: Exception){
            throw Exception(e)
        }

    }

}
package com.example.taskmobile.domain.usecases.board.board

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.repositories.BoardRepository
import com.example.taskmobile.data.services.BoardService

class FindAllUserBoardUseCase {

    private val repository: BoardRepository = BoardRepository(
        RetrofitClient.getRetrofitInstance().create(BoardService::class.java)
    )

    suspend fun execute(): List<Board>{

        return try{
            repository.findAll()
        }catch (e: Exception){
            throw Exception(e)
        }

    }

}
package com.example.taskmobile.domain.usecases.board.board

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.repositories.BoardRepository
import com.example.taskmobile.data.services.BoardService

class CreateBoardUseCase {
    private val repository: BoardRepository = BoardRepository(
        RetrofitClient.getRetrofitInstance().create(BoardService::class.java)
    )

    suspend fun execute(board: Board): Boolean{

        return try{
            repository.create(board)
            true
        }catch (e: Exception){
            false
        }

    }


}
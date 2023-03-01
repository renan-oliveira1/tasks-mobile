package com.example.taskmobile.domain.usecases.board.board

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.repositories.BoardRepository
import com.example.taskmobile.data.services.BoardService

class CreateBoardUseCase(private val repository: BoardRepository) {

    suspend fun execute(board: Board): Resource<Board>{

        return try{
            Resource.Success(repository.create(board))
        }catch (e: Exception){
            Resource.Error("Error to create board!!")
        }

    }


}
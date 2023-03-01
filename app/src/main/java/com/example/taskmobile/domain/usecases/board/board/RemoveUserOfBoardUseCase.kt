package com.example.taskmobile.domain.usecases.board.board

import com.example.taskmobile.core.Resource
import com.example.taskmobile.data.model.AddUserBoardModel
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.repositories.BoardRepository

class RemoveUserOfBoardUseCase(private val repository: BoardRepository) {

    suspend fun execute(idBoard: String, idUser: String): Resource<Board> {

        return try{
            val board = repository.findOne(idBoard)
            if(board == null)
                Resource.Error("Board not found!!", null)

            val addUserBoardModel = AddUserBoardModel(idUser, idBoard)
            Resource.Success(repository.deleteUserToBoard(addUserBoardModel))
        }catch (e: Exception){
            Resource.Error("Error to create board!!")
        }

    }

}
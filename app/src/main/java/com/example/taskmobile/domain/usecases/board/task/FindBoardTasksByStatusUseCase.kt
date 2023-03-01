package com.example.taskmobile.domain.usecases.board.task

import android.util.Log
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.BoardTaskStatus
import com.example.taskmobile.data.repositories.BoardRepository
import com.example.taskmobile.data.repositories.BoardTaskRepository

class FindBoardTasksByStatusUseCase(
    private val boardRepository: BoardRepository
) {
    suspend fun execute(id: String, status: BoardTaskStatus): List<BoardTask> {

        return try{
            val board = boardRepository.findOne(id)


            val tasks: List<BoardTask>? = board.tasks?.filter { it.status == status }

            tasks!!
        }catch (e: Exception){
            throw Exception(e)
        }

    }
}
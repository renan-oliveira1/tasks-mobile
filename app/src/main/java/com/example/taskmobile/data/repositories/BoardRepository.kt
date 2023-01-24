package com.example.taskmobile.data.repositories

import com.example.taskmobile.data.model.Board
import com.example.taskmobile.data.services.BoardService
import retrofit2.Call
import retrofit2.await

class BoardRepository(private val service: BoardService): Repository<Board, String> {
    override suspend fun create(board: Board): Board {
        return service.create(board.name).await()
    }

    override suspend fun findAll(): List<Board> {
        return service.findAll().await()
    }

    override suspend fun findOne(id: String): Board {
        return service.findOne(id).await()
    }

    override suspend fun update(t: Board): Board {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): Board {
        TODO("Not yet implemented")
    }
}
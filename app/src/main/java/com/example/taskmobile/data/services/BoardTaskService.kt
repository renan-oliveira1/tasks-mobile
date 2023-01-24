package com.example.taskmobile.data.services

import com.example.taskmobile.data.model.BoardTask
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface BoardTaskService {

    @PUT("board-tasks")
    fun create(@Body task: BoardTask): Call<BoardTask>

    @PUT("board-tasks")
    fun update(@Body task: BoardTask): Call<BoardTask>

    @DELETE("board-tasks/{id}")
    fun delete(@Path(value = "id") id: String): Call<BoardTask>

}
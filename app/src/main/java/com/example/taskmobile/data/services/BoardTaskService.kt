package com.example.taskmobile.data.services

import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.data.model.UpdateStatusBoardTask
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BoardTaskService {

    @POST("board-tasks")
    fun create(@Body task: SendBoardTask): Call<BoardTask>

    @GET("board-tasks/{id}")
    fun findOne(@Path(value = "id") id: String): Call<BoardTask>

    @PUT("board-tasks")
    fun update(@Body task: SendBoardTask): Call<BoardTask>

    @PATCH("board-tasks")
    fun updateStatus(@Body updateStatusBoardTask: UpdateStatusBoardTask): Call<BoardTask>

    @DELETE("board-tasks/{id}")
    fun delete(@Path(value = "id") id: String): Call<BoardTask>

}
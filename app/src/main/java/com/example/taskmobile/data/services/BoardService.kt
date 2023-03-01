package com.example.taskmobile.data.services

import com.example.taskmobile.data.model.AddUserBoardModel
import com.example.taskmobile.data.model.Board
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface BoardService {

    @POST("boards")
    fun create(@Body board: Board): Call<Board>

    @GET("boards")
    fun findAll(): Call<List<Board>>

    @GET("boards/{id}")
    fun findOne(@Path(value = "id") id: String): Call<Board>

    @PATCH("boards/add-user")
    fun addUser(@Body addUserBoardModel: AddUserBoardModel): Call<Board>

    @PATCH("boards/remove-user")
    fun removeUser(@Body addUserBoardModel: AddUserBoardModel): Call<Board>

}
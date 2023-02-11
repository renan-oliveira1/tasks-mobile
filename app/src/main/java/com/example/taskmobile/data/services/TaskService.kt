package com.example.taskmobile.data.services

import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.model.UpdateTaskStatusModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskService {

    @POST("tasks")
    fun create(@Body task: Task): Call<Task>

    @GET("tasks")
    fun findAll(): Call<List<Task>>

    @GET("tasks/{id}")
    fun findOne(@Path("id") id: String): Call<Task>

    @PUT("tasks")
    fun update(@Body task: Task): Call<Task>

    @PATCH("tasks")
    fun updateStatus(@Body updateTaskStatusModel: UpdateTaskStatusModel): Call<Task>

    @DELETE("tasks/{id}")
    fun delete(@Path("id") id: String): Call<Task>

}
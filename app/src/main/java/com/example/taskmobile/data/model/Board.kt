package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class Board(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String,
    @SerializedName("tasks") var tasks: List<BoardTask>?,
    @SerializedName("users") var users: List<UserModel>?
    )
package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class BoardTask(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("status") var status: BoardTaskStatus,
    @SerializedName("board") var board: String
)
package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class UpdateStatusBoardTask(
    @SerializedName("id") val id: String,
    @SerializedName("status") val status: BoardTaskStatus
)
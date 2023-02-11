package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class UpdateTaskStatusModel(
    @SerializedName("id") val id: String,
    @SerializedName("complete") val complete: Boolean
)

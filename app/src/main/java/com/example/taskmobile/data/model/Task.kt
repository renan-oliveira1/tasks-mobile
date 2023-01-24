package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("date") var date: String,
    @SerializedName("complete") var complete: Boolean?,
    @SerializedName("user") var user: String?
)

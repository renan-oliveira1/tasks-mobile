package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class AddUserBoardModel(
    @SerializedName("idUser") val idUser: String,
    @SerializedName("idBoard") val idBoard: String
)

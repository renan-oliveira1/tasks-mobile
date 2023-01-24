package com.example.taskmobile.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("username") var username: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String)
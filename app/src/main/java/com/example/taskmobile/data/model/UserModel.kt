package com.example.taskmobile.data.model

import com.example.taskmobile.presentation.ui.board.settings.adapter.USER_FILTER
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id") var id: String?,
    @SerializedName("username") var username: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    var userFilter: USER_FILTER?) {
    constructor(username: String, email: String, password: String) : this(null, username, email, password, null)
    constructor(username: String, email: String, password: String, userFilter: USER_FILTER?) : this(null, username, email, password, userFilter)
}
package com.example.taskmobile.presentation.ui.board.settings.adapter

import com.example.taskmobile.data.model.AddUserBoardModel

interface BoardUserListener {
    fun addUser(idUser: String)
    fun removeUser(idUser: String)
}
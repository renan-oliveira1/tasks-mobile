package com.example.taskmobile.presentation.ui.home.board.adapter

import com.example.taskmobile.data.model.Task

interface BoardActionListener {
    fun onTaskClick(id: String)
    fun onSettingClick(id: String)
}
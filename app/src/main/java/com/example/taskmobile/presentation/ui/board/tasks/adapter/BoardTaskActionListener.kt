package com.example.taskmobile.presentation.ui.board.tasks.adapter

import com.example.taskmobile.data.model.BoardTask

interface BoardTaskActionListener {
    fun onTaskClick(task: BoardTask)
    fun onCompleteClick(task: BoardTask)
    fun onEditClick(id: String)
    fun onDeleteClick(task: BoardTask)
    fun onUndoClick(task: BoardTask)
}
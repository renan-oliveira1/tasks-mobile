package com.example.taskmobile.presentation.ui.home.task.adapter

import com.example.taskmobile.data.model.Task

interface TaskActionListener {

    fun onCompleteClick(id: String)

    fun onEditClick(id: String)

    fun onDeleteClick(id: String)

    fun onTaskClick(task: Task)

}
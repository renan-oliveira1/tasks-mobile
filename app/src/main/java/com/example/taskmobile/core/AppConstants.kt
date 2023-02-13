package com.example.taskmobile.core

class AppConstants private constructor(){
    object HEADER{
        const val TOKEN = "authorization"
    }

    object BUNDLE{
        const val IDTASK = "idtask"
        const val TASK = "task"
    }

    object TASK_FILTER{
        const val DONE = 1
        const val UNDO = 0
    }

}
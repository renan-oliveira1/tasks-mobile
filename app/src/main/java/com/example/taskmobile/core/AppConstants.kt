package com.example.taskmobile.core

class AppConstants private constructor(){
    object HEADER{
        const val TOKEN = "authorization"
    }

    object BUNDLE{
        const val IDTASK = "idtask"
        const val TASK = "task"
        const val IDBOARD = "idboard"
        const val BOARD_TASK = "board_task"
    }

    object TASK_FILTER{
        const val DONE = 1
        const val UNDO = 0
    }

    object SHARED_PREFERENCES{
        const val ID_BOARD = "id_board"
    }

    object USER_FILTER{
        const val IN_BOARD = "in_board"
        const val OUT_BOARD = "out_board"
    }

}
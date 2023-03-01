package com.example.taskmobile.domain.usecases.board

import com.example.taskmobile.domain.usecases.board.board.*
import com.example.taskmobile.domain.usecases.board.task.*

class BoardUseCases(
    val createBoardUseCase: CreateBoardUseCase,
    val findAllUserBoardUseCase: FindAllUserBoardUseCase,
    val findOneUserBoardUseCase: FindOneUserBoardUseCase,
    val createBoardTaskUseCase: CreateBoardTaskUseCase,
    val findBoardTasksByStatusUseCase: FindBoardTasksByStatusUseCase,
    val findOneBoardTaskUseCase: FindOneBoardTaskUseCase,
    val updateBoardTaskUseCase: UpdateBoardTaskUseCase,
    val updateStatusBoardTaskUseCase: UpdateStatusBoardTaskUseCase,
    val deleteBoardTaskUseCase: DeleteBoardTaskUseCase,
    val addUserToBoardUseCase: AddUserToBoardUseCase,
    val removeUserOfBoardUseCase: RemoveUserOfBoardUseCase
) {
}
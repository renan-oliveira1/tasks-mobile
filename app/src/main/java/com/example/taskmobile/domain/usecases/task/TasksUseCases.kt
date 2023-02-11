package com.example.taskmobile.domain.usecases.task

class TasksUseCases(
    val createTaskUseCase: CreateTaskUseCase,
    val findAllDoneTaskUseCase: FindAllDoneTaskUseCase,
    val findAllUndoTaskUseCase: FindAllUndoTaskUseCase,
    val findOneTaskUseCase: FindOneTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val updateStatusTaskUseCase: UpdateStatusTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase
) {
}
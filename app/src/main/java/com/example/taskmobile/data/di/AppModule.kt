package com.example.taskmobile.data.di

import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.services.TaskService
import com.example.taskmobile.domain.usecases.board.board.CreateBoardUseCase
import com.example.taskmobile.domain.usecases.board.board.FindAllUserBoardUseCase
import com.example.taskmobile.domain.usecases.task.*
import com.example.taskmobile.domain.usecases.user.LoginUseCase
import com.example.taskmobile.domain.usecases.user.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun loginUseCase(): LoginUseCase = LoginUseCase()

    @Provides
    @Singleton
    fun registerUseCase(): RegisterUseCase = RegisterUseCase()

    @Provides
    @Singleton
    fun createBoardUseCase(): CreateBoardUseCase = CreateBoardUseCase()

    @Provides
    @Singleton
    fun findAllBoardUserUseCase(): FindAllUserBoardUseCase = FindAllUserBoardUseCase()

    private val taskRepository: TaskRepository = TaskRepository(
        RetrofitClient.getRetrofitInstance().create(TaskService::class.java)
    )

    @Provides
    @Singleton
    fun tasksUseCases(): TasksUseCases = TasksUseCases(
        CreateTaskUseCase(taskRepository),
        FindAllDoneTaskUseCase(taskRepository),
        FindAllUndoTaskUseCase(taskRepository),
        FindOneTaskUseCase(taskRepository),
        UpdateTaskUseCase(taskRepository),
        UpdateStatusTaskUseCase(taskRepository),
        DeleteTaskUseCase(taskRepository)
    )



}
package com.example.taskmobile.core.di

import com.example.taskmobile.data.di.RetrofitClient
import com.example.taskmobile.data.repositories.BoardRepository
import com.example.taskmobile.data.repositories.BoardTaskRepository
import com.example.taskmobile.data.repositories.TaskRepository
import com.example.taskmobile.data.repositories.UserRepository
import com.example.taskmobile.data.services.BoardService
import com.example.taskmobile.data.services.BoardTaskService
import com.example.taskmobile.data.services.TaskService
import com.example.taskmobile.data.services.UserService
import com.example.taskmobile.domain.usecases.board.BoardUseCases
import com.example.taskmobile.domain.usecases.board.board.*
import com.example.taskmobile.domain.usecases.board.task.*
import com.example.taskmobile.domain.usecases.task.*
import com.example.taskmobile.domain.usecases.user.GetUsersUseCase
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


    private val taskRepository: TaskRepository = TaskRepository(
        RetrofitClient.getRetrofitInstance().create(TaskService::class.java)
    )

    private val boardRepository: BoardRepository = BoardRepository(
        RetrofitClient.getRetrofitInstance().create(BoardService::class.java)
    )

    private val boardTaskRepository: BoardTaskRepository = BoardTaskRepository(
        RetrofitClient.getRetrofitInstance().create(BoardTaskService::class.java)
    )

    private val userRepository: UserRepository = UserRepository(
        RetrofitClient.getRetrofitInstance().create(UserService::class.java)
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

    @Provides
    @Singleton
    fun boardUseCases(): BoardUseCases = BoardUseCases(
        CreateBoardUseCase(boardRepository),
        FindAllUserBoardUseCase(boardRepository),
        FindOneUserBoardUseCase(boardRepository),
        CreateBoardTaskUseCase(boardTaskRepository),
        FindBoardTasksByStatusUseCase(boardRepository),
        FindOneBoardTaskUseCase(boardTaskRepository),
        UpdateBoardTaskUseCase(boardTaskRepository),
        UpdateStatusBoardTaskUseCase(boardTaskRepository),
        DeleteBoardTaskUseCase(boardTaskRepository),
        AddUserToBoardUseCase(boardRepository),
        RemoveUserOfBoardUseCase(boardRepository)
    )

    @Provides
    @Singleton
    fun getUsersUseCase(): GetUsersUseCase = GetUsersUseCase(userRepository)



}
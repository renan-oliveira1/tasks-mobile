package com.example.taskmobile.data.di

import com.example.taskmobile.domain.usecases.board.board.FindAllUserBoardUseCase
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
    fun findAllBoardUser(): FindAllUserBoardUseCase = FindAllUserBoardUseCase()

}
package com.example.taskmobile.presentation.ui.home.board.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.domain.usecases.board.board.CreateBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDialogViewModel @Inject constructor(
    private val createBoardUseCase: CreateBoardUseCase
    ): ViewModel() {


}
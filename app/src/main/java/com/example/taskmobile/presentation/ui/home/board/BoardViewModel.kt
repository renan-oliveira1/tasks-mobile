package com.example.taskmobile.presentation.ui.home.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.domain.usecases.board.BoardUseCases
import com.example.taskmobile.domain.usecases.board.board.CreateBoardUseCase
import com.example.taskmobile.domain.usecases.board.board.FindAllUserBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardUseCases: BoardUseCases
    ): ViewModel(){

    private var _state = MutableLiveData<List<Board>>()
    var state: LiveData<List<Board>> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loadBoards(){
        viewModelScope.launch {
            val boards = boardUseCases.findAllUserBoardUseCase.execute()
            _state.postValue(boards)
        }
    }

    fun createBoard(name: String){
        val board = Board(name = name)
        viewModelScope.launch {
            val result = boardUseCases.createBoardUseCase.execute(board)
            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("Successfully logged!!"))
                    loadBoards()
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }

    }

}
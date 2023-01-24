package com.example.taskmobile.presentation.ui.home.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.data.model.Board
import com.example.taskmobile.domain.usecases.board.board.FindAllUserBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val findAllUserBoardUseCase: FindAllUserBoardUseCase
    ): ViewModel(){

    private var _state = MutableLiveData<List<Board>>()
    var state: LiveData<List<Board>> = _state

    fun loadBoards(){
        viewModelScope.launch {
            val boards = findAllUserBoardUseCase.execute()
            _state.postValue(boards)
        }
    }

}
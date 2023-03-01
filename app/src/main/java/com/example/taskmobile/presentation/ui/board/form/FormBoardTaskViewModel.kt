package com.example.taskmobile.presentation.ui.board.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.domain.usecases.board.BoardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormBoardTaskViewModel @Inject constructor(
    private val boardUseCases: BoardUseCases
): ViewModel() {

    private var _editTask = MutableLiveData<BoardTask>()
    var editTask: LiveData<BoardTask> = _editTask

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loadTask(id: String){
        viewModelScope.launch {
            _editTask.postValue(boardUseCases.findOneBoardTaskUseCase.execute(id))
        }
    }

    fun createTask(task: SendBoardTask){
        viewModelScope.launch {
            val result = boardUseCases.createBoardTaskUseCase.execute(task)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowToast("Task created successfully!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }

        }
    }

    fun updateTask(task: SendBoardTask){
        viewModelScope.launch {
            val result = boardUseCases.updateBoardTaskUseCase.execute(task)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowToast("Task updated successfully!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }

        }
    }

}
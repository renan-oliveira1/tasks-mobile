package com.example.taskmobile.presentation.ui.board.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.BoardTaskStatus
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.domain.usecases.board.BoardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardTasksViewModel @Inject constructor (
    private val boardUseCases: BoardUseCases
    ) : ViewModel(){

    private var _state = MutableLiveData<List<BoardTask>>()
    var state: LiveData<List<BoardTask>> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private lateinit var deletedTask: SendBoardTask

    fun loadTasks(id: String, status: BoardTaskStatus){
        viewModelScope.launch {
            _state.postValue(
                boardUseCases.findBoardTasksByStatusUseCase.execute(id, status)
            )
        }
    }

    fun updateStatusTask(id: String, status: BoardTaskStatus, idBoard: String){
        viewModelScope.launch {
            boardUseCases.updateStatusBoardTaskUseCase.execute(id, status)
            loadTasks(idBoard, status)
        }
    }

    fun deleteTask(task: BoardTask){
        viewModelScope.launch {
            val result = boardUseCases.deleteBoardTaskUseCase.execute(task.id!!)

            when(result){
                is Resource.Success -> {
                    deletedTask = SendBoardTask(
                        task.id,
                        task.name,
                        task.description,
                        task.status,
                        task.board
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar("Task deleted successfully!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }

    fun undoDelete(){
        viewModelScope.launch {
            val result = boardUseCases.createBoardTaskUseCase.execute(deletedTask)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowToast("Task recovery successfully!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }

}
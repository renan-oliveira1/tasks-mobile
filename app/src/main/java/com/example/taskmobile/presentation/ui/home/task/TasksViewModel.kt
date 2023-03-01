package com.example.taskmobile.presentation.ui.home.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.model.UpdateTaskStatusModel
import com.example.taskmobile.domain.usecases.task.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksUseCases: TasksUseCases
): ViewModel(){

    private var _state = MutableLiveData<List<Task>>()
    var state: LiveData<List<Task>> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private lateinit var deletedTask: Task

    fun loadTasks(taskFilter: Int){
        viewModelScope.launch {
            if(taskFilter == AppConstants.TASK_FILTER.UNDO) _state.postValue(tasksUseCases.findAllUndoTaskUseCase.execute())
            if(taskFilter == AppConstants.TASK_FILTER.DONE) _state.postValue(tasksUseCases.findAllDoneTaskUseCase.execute())
        }
    }

    fun deleteTask(id: String, taskFilter: Int){
        viewModelScope.launch {
            val result = tasksUseCases.deleteTaskUseCase.execute(id)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("Successfully deleted!!"))
                    loadTasks(taskFilter)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }

    fun undoDelete(taskFilter: Int){
        viewModelScope.launch {
            tasksUseCases.createTaskUseCase.execute(deletedTask)
            loadTasks(taskFilter)
        }
    }

    fun updateStatusTask(updateStatusTask: UpdateTaskStatusModel, taskFilter: Int){

        viewModelScope.launch {
            tasksUseCases.updateStatusTaskUseCase.execute(updateStatusTask)
            loadTasks(taskFilter)
        }
    }

}
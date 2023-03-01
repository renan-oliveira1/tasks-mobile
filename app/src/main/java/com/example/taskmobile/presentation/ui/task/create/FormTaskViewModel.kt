package com.example.taskmobile.presentation.ui.task.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.domain.usecases.task.CreateTaskUseCase
import com.example.taskmobile.domain.usecases.task.FindOneTaskUseCase
import com.example.taskmobile.domain.usecases.task.TasksUseCases
import com.example.taskmobile.domain.usecases.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormTaskViewModel @Inject constructor(
    private val tasksUseCases: TasksUseCases
) : ViewModel(){

    private var _editTask = MutableLiveData<Task>()
    var editTask: LiveData<Task> = _editTask

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun createTask(task: Task){
        viewModelScope.launch {
            val result = tasksUseCases.createTaskUseCase.execute(task)
            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("Task created successfully!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }

    fun findTask(id: String){
        viewModelScope.launch {
            val result = tasksUseCases.findOneTaskUseCase.execute(id)
            when(result){
                is Resource.Success -> {
                    _editTask.postValue(result.data!!)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText.toString()))
                }
            }
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            val result = tasksUseCases.updateTaskUseCase.execute(task)
            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("Task updated successfully!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText.toString()))
                }
            }
        }
    }

}
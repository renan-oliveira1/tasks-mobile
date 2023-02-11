package com.example.taskmobile.presentation.ui.home.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.data.model.UpdateTaskStatusModel
import com.example.taskmobile.domain.usecases.task.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksUseCases: TasksUseCases
): ViewModel(){

    private var _state = MutableLiveData<List<Task>>()
    var state: LiveData<List<Task>> = _state

    private lateinit var deletedTask: Task

    fun loadTasks(){
        viewModelScope.launch {
            _state.postValue(tasksUseCases.findAllUndoTaskUseCase.execute())
        }
    }

    fun deleteTask(id: String){
        viewModelScope.launch {
            deletedTask = tasksUseCases.deleteTaskUseCase.execute(id)
            loadTasks()
        }
    }

    fun undoDelete(){
        viewModelScope.launch {
            tasksUseCases.createTaskUseCase.execute(deletedTask)
            loadTasks()
        }
    }

    fun updateStatusTask(updateStatusTask: UpdateTaskStatusModel){

        viewModelScope.launch {
            tasksUseCases.updateStatusTaskUseCase.execute(updateStatusTask)
            loadTasks()
        }
    }

}
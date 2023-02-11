package com.example.taskmobile.presentation.ui.task.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.domain.usecases.task.CreateTaskUseCase
import com.example.taskmobile.domain.usecases.task.FindOneTaskUseCase
import com.example.taskmobile.domain.usecases.task.TasksUseCases
import com.example.taskmobile.domain.usecases.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormTaskViewModel @Inject constructor(
    private val tasksUseCases: TasksUseCases
) : ViewModel(){

    private var _editTask = MutableLiveData<Task>()
    var editTask: LiveData<Task> = _editTask

    fun createTask(task: Task){
        viewModelScope.launch {
            tasksUseCases.createTaskUseCase.execute(task)
        }
    }

    fun findTask(id: String){
        viewModelScope.launch {
            _editTask.postValue(tasksUseCases.findOneTaskUseCase.execute(id))
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            tasksUseCases.updateTaskUseCase.execute(task)
        }
    }

}
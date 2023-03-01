package com.example.taskmobile.presentation.ui.board.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.AddUserBoardModel
import com.example.taskmobile.data.model.BoardTask
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.domain.usecases.board.BoardUseCases
import com.example.taskmobile.domain.usecases.user.GetUsersUseCase
import com.example.taskmobile.presentation.ui.board.settings.adapter.USER_FILTER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardSettingsViewModel @Inject constructor(
    private val boardUseCases: BoardUseCases,
    private val getUsersUseCase: GetUsersUseCase
):ViewModel() {

    private var _state = MutableLiveData<List<UserModel>>()
    var state: LiveData<List<UserModel>> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loadBoardManagers(id: String){
        viewModelScope.launch {
            val board = boardUseCases.findOneUserBoardUseCase.execute(id)
            _state.postValue(
                board.users!!
            )
        }
    }

    fun loadUsers(id: String){
        viewModelScope.launch {
            val board = boardUseCases.findOneUserBoardUseCase.execute(id)
            val users = getUsersUseCase.execute()

            val usersNotInBoard: List<UserModel> = users.data?.filter { !board.users!!.contains(it) }!!
            val setTypeUser = ArrayList<UserModel>()
            usersNotInBoard.forEach {
                val value = it
                value.userFilter = USER_FILTER.OUT
                setTypeUser.add(value)
            }
            _state.postValue(
                setTypeUser
            )
        }
    }

    fun addUserToBoard(idBoard: String, idUser: String){
        viewModelScope.launch {
            val result = boardUseCases.addUserToBoardUseCase.execute(idBoard = idBoard, idUser = idUser)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("User added successfully!!"))
                    loadUsers(idBoard)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }

    fun removeUserBoard(idBoard: String, idUser: String){
        viewModelScope.launch {
            val result = boardUseCases.removeUserOfBoardUseCase.execute(idBoard = idBoard, idUser = idUser)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("User removed successfully!!"))
                    loadBoardManagers(idBoard)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }
}
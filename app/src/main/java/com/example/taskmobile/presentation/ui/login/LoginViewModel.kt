package com.example.taskmobile.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.SendBoardTask
import com.example.taskmobile.domain.usecases.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
    ): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun login(email: String, password: String){
        viewModelScope.launch {
            val result = loginUseCase.execute(email, password)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("Successfully logged!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }

}
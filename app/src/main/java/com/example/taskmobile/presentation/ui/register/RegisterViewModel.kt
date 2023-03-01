package com.example.taskmobile.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.core.Resource
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.domain.usecases.user.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun register(username: String, email: String, password: String){
        val user = UserModel(username, email, password)

        viewModelScope.launch {
            val result = registerUseCase.execute(user)

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar("Successfully registered!!"))
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowToast(result.uiText.toString()))
                }
            }
        }
    }

}
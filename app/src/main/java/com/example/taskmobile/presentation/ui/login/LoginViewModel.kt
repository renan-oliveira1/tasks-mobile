package com.example.taskmobile.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.domain.usecases.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
    ): ViewModel() {

    private val _status= MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun login(email: String, password: String){
        viewModelScope.launch {
            _status.postValue(loginUseCase.execute(email, password))
        }
    }

}
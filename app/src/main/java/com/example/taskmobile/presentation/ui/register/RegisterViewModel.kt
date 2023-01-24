package com.example.taskmobile.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmobile.data.model.UserModel
import com.example.taskmobile.domain.usecases.user.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _status= MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun register(username: String, email: String, password: String){
        val user = UserModel(username, email, password)

        viewModelScope.launch {
            _status.postValue(registerUseCase.execute(user))
        }
    }

}
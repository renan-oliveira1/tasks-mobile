package com.example.taskmobile.core

sealed class UiEvent {
    data class ShowToast(val text: String): UiEvent()
    data class ShowSnackbar(val text: String): UiEvent()
}

package com.example.core.utils

sealed interface UiEvent {

    data class ShowSnackBar(val message: UiText) : UiEvent
    data class OnNextScreen(val route: String) : UiEvent
    object OnBack : UiEvent

}
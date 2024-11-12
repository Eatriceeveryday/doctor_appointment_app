package com.mufr.klinikku.presentation.ui

sealed class UiEvent{
    data class SnackBarEvent(val message: String): UiEvent()
    object NavigateEvent: UiEvent()
    object NavigateInvalidAuthEvent: UiEvent()

}

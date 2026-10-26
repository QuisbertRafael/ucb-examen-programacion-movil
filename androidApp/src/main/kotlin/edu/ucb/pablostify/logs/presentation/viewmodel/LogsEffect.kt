package edu.ucb.pablostify.logs.presentation.viewmodel

sealed interface LogsEffect {
    data class ShowToast(val message: String) : LogsEffect
}

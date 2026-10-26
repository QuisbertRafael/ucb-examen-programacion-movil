package edu.ucb.pablostify.logs.presentation.viewmodel

data class LogsState(
    val isLoading: Boolean = false,
    val resultCode: String? = null,
    val error: String? = null
)

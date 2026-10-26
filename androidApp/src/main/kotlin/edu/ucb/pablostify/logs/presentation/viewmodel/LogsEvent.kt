package edu.ucb.pablostify.logs.presentation.viewmodel

sealed interface LogsEvent {
    object SendTestLog : LogsEvent
}

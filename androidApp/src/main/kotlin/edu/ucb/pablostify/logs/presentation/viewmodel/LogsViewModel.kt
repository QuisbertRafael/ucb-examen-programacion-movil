package edu.ucb.pablostify.logs.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.pablostify.logs.domain.usecase.SendTestLogUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogsViewModel(
    private val sendTestLogUseCase: SendTestLogUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LogsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LogsEffect>()
    val effect = _effect.asSharedFlow()

    fun emitEvent(event: LogsEvent) {
        when (event) {
            LogsEvent.SendTestLog -> sendTestLog()
        }
    }

    private fun sendTestLog() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, resultCode = null, error = null) }
            sendTestLogUseCase().fold(
                onSuccess = { response ->
                    val resultName = response.resultCode.name
                    _state.update { it.copy(isLoading = false, resultCode = resultName, error = null) }
                },
                onFailure = { e ->
                    val errorMessage = e.message ?: e.toString()
                    _state.update { it.copy(isLoading = false, resultCode = null, error = errorMessage) }
                    _effect.emit(LogsEffect.ShowToast(errorMessage))
                }
            )
        }
    }
}

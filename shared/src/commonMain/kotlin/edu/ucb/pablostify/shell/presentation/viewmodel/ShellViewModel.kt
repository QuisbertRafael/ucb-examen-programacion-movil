package edu.ucb.pablostify.shell.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.pablostify.remoteconfig.domain.usecase.ObserveRemoteConfigUseCase
import edu.ucb.pablostify.remoteconfig.domain.usecase.RefreshRemoteConfigUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel de "toda la app": descarga Remote Config al abrir y deja los flags
 * (modo mantenimiento, notas habilitadas, mensaje) disponibles para la navegacion.
 */
class ShellViewModel(
    observeRemoteConfigUseCase: ObserveRemoteConfigUseCase,
    private val refreshRemoteConfigUseCase: RefreshRemoteConfigUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShellState(config = observeRemoteConfigUseCase().value))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeRemoteConfigUseCase().collect { cfg -> _state.update { it.copy(config = cfg) } }
        }
        viewModelScope.launch { refreshRemoteConfigUseCase() }
    }
}

package edu.ucb.pablostify.starwars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.pablostify.starwars.domain.usecase.GetStarWarsCharactersUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StarWarsViewModel(private val useCase: GetStarWarsCharactersUseCase) : ViewModel() {
    private val _effect = MutableSharedFlow<StarWarsEffect>()
    val effect = _effect.asSharedFlow()
    private val _state = MutableStateFlow(StarWarsState())
    val state = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun emitEvent(event: StarWarsEvent) {
        when (event) {
            StarWarsEvent.Retry -> loadCharacters()
        }
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            useCase().fold(
                onSuccess = { characters -> _state.update { it.copy(isLoading = false, characters = characters) } },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    _effect.emit(StarWarsEffect.ShowToast(e.message ?: "Error"))
                }
            )
        }
    }
}

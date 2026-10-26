package edu.ucb.pablostify.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.pablostify.notes.domain.usecase.AddNoteUseCase
import edu.ucb.pablostify.notes.domain.usecase.DeleteNoteUseCase
import edu.ucb.pablostify.notes.domain.usecase.ObserveNotesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private var observeJob: Job? = null

    init { observe() }

    fun emitEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.OnDraftChanged -> _state.update { it.copy(draft = event.value) }
            NotesEvents.OnAdd -> add()
            is NotesEvents.OnDelete -> delete(event.id)
            NotesEvents.OnRetry -> observe()
        }
    }

    // Escucha la base de datos en tiempo real: cada cambio remoto llega aqui solo.
    private fun observe() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            observeNotesUseCase().collect { result ->
                result.fold(
                    onSuccess = { notes -> _state.update { it.copy(isLoading = false, error = null, notes = notes) } },
                    onFailure = { e -> _state.update { it.copy(isLoading = false, error = e.message ?: "Error") } }
                )
            }
        }
    }

    private fun add() {
        val text = _state.value.draft
        viewModelScope.launch {
            addNoteUseCase(text).fold(
                onSuccess = { _state.update { it.copy(draft = "", error = null) } },
                onFailure = { e -> _state.update { it.copy(error = e.message ?: "Error") } }
            )
        }
    }

    private fun delete(id: String) {
        viewModelScope.launch {
            deleteNoteUseCase(id).onFailure { e -> _state.update { it.copy(error = e.message ?: "Error") } }
        }
    }
}

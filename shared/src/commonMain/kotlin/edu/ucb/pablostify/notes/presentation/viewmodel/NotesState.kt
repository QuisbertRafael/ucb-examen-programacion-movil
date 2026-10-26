package edu.ucb.pablostify.notes.presentation.viewmodel

import edu.ucb.pablostify.notes.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val draft: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)

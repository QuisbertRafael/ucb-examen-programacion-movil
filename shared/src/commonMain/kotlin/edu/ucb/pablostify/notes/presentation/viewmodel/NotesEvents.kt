package edu.ucb.pablostify.notes.presentation.viewmodel

sealed interface NotesEvents {
    data class OnDraftChanged(val value: String) : NotesEvents
    data object OnAdd : NotesEvents
    data class OnDelete(val id: String) : NotesEvents
    data object OnRetry : NotesEvents
}

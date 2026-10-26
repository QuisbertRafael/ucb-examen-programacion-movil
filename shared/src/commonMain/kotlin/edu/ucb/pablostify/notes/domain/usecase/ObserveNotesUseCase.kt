package edu.ucb.pablostify.notes.domain.usecase

import edu.ucb.pablostify.notes.domain.model.Note
import edu.ucb.pablostify.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class ObserveNotesUseCase(private val repository: NotesRepository) {
    operator fun invoke(): Flow<Result<List<Note>>> = repository.observeNotes()
}

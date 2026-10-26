package edu.ucb.pablostify.notes.domain.usecase

import edu.ucb.pablostify.notes.domain.repository.NotesRepository

class DeleteNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(id: String): Result<Unit> = repository.deleteNote(id)
}

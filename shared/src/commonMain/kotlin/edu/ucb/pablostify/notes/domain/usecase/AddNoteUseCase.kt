package edu.ucb.pablostify.notes.domain.usecase

import edu.ucb.pablostify.notes.domain.repository.NotesRepository

class AddNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(text: String): Result<Unit> {
        val clean = text.trim()
        if (clean.isEmpty()) return Result.failure(IllegalArgumentException("La nota no puede estar vacia"))
        return repository.addNote(clean)
    }
}

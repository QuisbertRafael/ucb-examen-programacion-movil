package edu.ucb.pablostify.notes.domain.repository

import edu.ucb.pablostify.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun observeNotes(): Flow<Result<List<Note>>>
    suspend fun addNote(text: String): Result<Unit>
    suspend fun deleteNote(id: String): Result<Unit>
}

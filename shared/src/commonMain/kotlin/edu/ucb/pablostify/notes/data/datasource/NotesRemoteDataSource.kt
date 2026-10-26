package edu.ucb.pablostify.notes.data.datasource

import edu.ucb.pablostify.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Fuente de datos en tiempo real. Android: Firebase Realtime Database (FirebaseNotesDataSource).
 * iOS: memoria (InMemoryNotesDataSource).
 */
interface NotesRemoteDataSource {
    /** Emite la lista completa cada vez que algo cambia en el servidor (tiempo real). */
    fun observeNotes(): Flow<Result<List<Note>>>
    suspend fun addNote(text: String): Result<Unit>
    suspend fun deleteNote(id: String): Result<Unit>
}

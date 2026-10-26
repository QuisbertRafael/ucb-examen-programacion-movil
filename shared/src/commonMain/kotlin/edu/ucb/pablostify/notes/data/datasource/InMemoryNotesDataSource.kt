package edu.ucb.pablostify.notes.data.datasource

import edu.ucb.pablostify.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/** Implementacion local (sin Firebase). La usa iOS y sirve de ejemplo de como se ve el contrato. */
class InMemoryNotesDataSource : NotesRemoteDataSource {
    private val notes = MutableStateFlow<List<Note>>(emptyList())
    private var counter = 0L

    override fun observeNotes(): Flow<Result<List<Note>>> =
        notes.map { list -> Result.success(list.sortedByDescending { it.createdAt }) }

    override suspend fun addNote(text: String): Result<Unit> {
        counter += 1
        val note = Note(id = counter.toString(), text = text, createdAt = counter)
        notes.update { it + note }
        return Result.success(Unit)
    }

    override suspend fun deleteNote(id: String): Result<Unit> {
        notes.update { list -> list.filterNot { it.id == id } }
        return Result.success(Unit)
    }
}

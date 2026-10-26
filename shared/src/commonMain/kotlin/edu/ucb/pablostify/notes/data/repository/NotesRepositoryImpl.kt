package edu.ucb.pablostify.notes.data.repository

import edu.ucb.pablostify.notes.data.datasource.NotesRemoteDataSource
import edu.ucb.pablostify.notes.domain.model.Note
import edu.ucb.pablostify.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(private val dataSource: NotesRemoteDataSource) : NotesRepository {
    override fun observeNotes(): Flow<Result<List<Note>>> = dataSource.observeNotes()
    override suspend fun addNote(text: String): Result<Unit> = dataSource.addNote(text)
    override suspend fun deleteNote(id: String): Result<Unit> = dataSource.deleteNote(id)
}

package edu.ucb.pablostify.notes.data.repository

import edu.ucb.pablostify.notes.data.datasource.NotesRemoteDataSource
import edu.ucb.pablostify.notes.domain.model.Note
import edu.ucb.pablostify.notes.domain.repository.NotesRepository
import edu.ucb.pablostify.scheduler.LogUploader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NotesRepositoryImpl(
    private val dataSource: NotesRemoteDataSource,
    private val logUploader: LogUploader? = null
) : NotesRepository {

    override fun observeNotes(): Flow<Result<List<Note>>> {
        return dataSource.observeNotes().onEach { result ->
            result.onFailure { e ->
                logUploader?.upload(
                    message = "Firebase Notes: Error al observar notas: ${e.message}",
                    stackTrace = e.stackTraceToString()
                )
            }
        }
    }

    override suspend fun addNote(text: String): Result<Unit> {
        val result = dataSource.addNote(text)
        result.onSuccess {
            logUploader?.upload(message = "Firebase Notes: Nota creada exitosamente -> '$text'")
        }.onFailure { e ->
            logUploader?.upload(
                message = "Firebase Notes: Error al crear nota -> '$text'",
                stackTrace = e.stackTraceToString()
            )
        }
        return result
    }

    override suspend fun deleteNote(id: String): Result<Unit> {
        val result = dataSource.deleteNote(id)
        result.onSuccess {
            logUploader?.upload(message = "Firebase Notes: Nota eliminada (ID: $id)")
        }.onFailure { e ->
            logUploader?.upload(
                message = "Firebase Notes: Error al eliminar nota (ID: $id)",
                stackTrace = e.stackTraceToString()
            )
        }
        return result
    }
}

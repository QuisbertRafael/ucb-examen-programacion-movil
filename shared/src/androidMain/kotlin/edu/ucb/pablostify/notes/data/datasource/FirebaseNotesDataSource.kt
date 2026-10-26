package edu.ucb.pablostify.notes.data.datasource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.ucb.pablostify.core.firebase.FIREBASE_NOT_CONFIGURED_MESSAGE
import edu.ucb.pablostify.notes.domain.model.Note
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Firebase Realtime Database. Estructura en el arbol JSON:
 *
 *   notes
 *    +-- <id generado por push()>
 *         +-- text:      "mi nota"
 *         +-- createdAt: 1730000000000
 *
 * La API key NO se escribe aqui: el plugin google-services la toma de androidApp/google-services.json.
 */
class FirebaseNotesDataSource(private val context: Context) : NotesRemoteDataSource {

    private fun notesReference(): Result<DatabaseReference> {
        if (FirebaseApp.getApps(context).isEmpty()) {
            return Result.failure(IllegalStateException(FIREBASE_NOT_CONFIGURED_MESSAGE))
        }
        return try {
            Result.success(FirebaseDatabase.getInstance().getReference(NOTES_PATH))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeNotes(): Flow<Result<List<Note>>> = callbackFlow {
        val reference = notesReference().getOrElse { error ->
            trySend(Result.failure(error))
            awaitClose()
            return@callbackFlow
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notes = snapshot.children.mapNotNull { child ->
                    val text = child.child("text").getValue(String::class.java) ?: return@mapNotNull null
                    val createdAt = child.child("createdAt").getValue(Long::class.java) ?: 0L
                    Note(id = child.key.orEmpty(), text = text, createdAt = createdAt)
                }.sortedByDescending { it.createdAt }
                trySend(Result.success(notes))
            }

            override fun onCancelled(error: DatabaseError) {
                // Normalmente: PERMISSION_DENIED (revisa las reglas de la base de datos).
                trySend(Result.failure(error.toException()))
            }
        }

        reference.addValueEventListener(listener)
        awaitClose { reference.removeEventListener(listener) }
    }

    override suspend fun addNote(text: String): Result<Unit> = try {
        val reference = notesReference().getOrThrow()
        val key = reference.push().key ?: error("No se pudo generar el id de la nota")
        // Sin await: Realtime Database aplica el cambio local al instante y sincroniza cuando hay red.
        reference.child(key).setValue(mapOf("text" to text, "createdAt" to System.currentTimeMillis()))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteNote(id: String): Result<Unit> = try {
        notesReference().getOrThrow().child(id).removeValue()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private companion object {
        const val NOTES_PATH = "notes"
    }
}

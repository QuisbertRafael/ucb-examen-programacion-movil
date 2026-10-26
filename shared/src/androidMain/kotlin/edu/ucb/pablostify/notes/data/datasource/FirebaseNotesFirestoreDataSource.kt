package edu.ucb.pablostify.notes.data.datasource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.ucb.pablostify.core.firebase.FIREBASE_NOT_CONFIGURED_MESSAGE
import edu.ucb.pablostify.notes.domain.model.Note
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Fuente de datos con Cloud Firestore.
 * Estructura en Firestore: Colección "notes" con documentos que contienen "text" y "createdAt".
 */
class FirebaseNotesFirestoreDataSource(private val context: Context) : NotesRemoteDataSource {

    private fun firestoreInstance(): Result<FirebaseFirestore> {
        if (FirebaseApp.getApps(context).isEmpty()) {
            return Result.failure(IllegalStateException(FIREBASE_NOT_CONFIGURED_MESSAGE))
        }
        return try {
            Result.success(FirebaseFirestore.getInstance())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeNotes(): Flow<Result<List<Note>>> = callbackFlow {
        val db = firestoreInstance().getOrElse { error ->
            trySend(Result.failure(error))
            awaitClose()
            return@callbackFlow
        }

        val listener = db.collection(NOTES_COLLECTION)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Result.failure(error))
                    return@addSnapshotListener
                }

                val notes = snapshot?.documents?.mapNotNull { doc ->
                    val text = doc.getString("text") ?: return@mapNotNull null
                    val createdAt = doc.getLong("createdAt") ?: 0L
                    Note(id = doc.id, text = text, createdAt = createdAt)
                } ?: emptyList()

                trySend(Result.success(notes))
            }

        awaitClose { listener.remove() }
    }

    override suspend fun addNote(text: String): Result<Unit> = try {
        val db = firestoreInstance().getOrThrow()
        val noteData = mapOf(
            "text" to text,
            "createdAt" to System.currentTimeMillis()
        )
        db.collection(NOTES_COLLECTION).add(noteData).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteNote(id: String): Result<Unit> = try {
        val db = firestoreInstance().getOrThrow()
        db.collection(NOTES_COLLECTION).document(id).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private companion object {
        const val NOTES_COLLECTION = "notes"
    }
}

package edu.ucb.pablostify.core.push

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import edu.ucb.pablostify.core.firebase.FIREBASE_NOT_CONFIGURED_MESSAGE
import edu.ucb.pablostify.core.firebase.awaitTask

class FirebasePushTokenProvider(private val context: Context) : PushTokenProvider {
    override suspend fun getToken(): Result<String> {
        if (FirebaseApp.getApps(context).isEmpty()) {
            return Result.failure(IllegalStateException(FIREBASE_NOT_CONFIGURED_MESSAGE))
        }
        return try {
            Result.success(FirebaseMessaging.getInstance().token.awaitTask())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

package edu.ucb.pablostify

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Servicio de Firebase Cloud Messaging.
 * - Con la app en SEGUNDO PLANO, FCM muestra solo las notificaciones con payload "notification".
 * - Con la app ABIERTA (o con mensajes "data"), llega a onMessageReceived y la mostramos nosotros.
 */
class PablostifyMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title ?: message.data["title"] ?: getString(R.string.app_name)
        val body = message.notification?.body ?: message.data["body"] ?: return
        NotificationHelper.show(this, title, body)
    }

    override fun onNewToken(token: String) {
        // Si tuvieras backend, aqui enviarias el token nuevo para guardarlo.
        Log.d("FCM", "Nuevo token: $token")
    }
}

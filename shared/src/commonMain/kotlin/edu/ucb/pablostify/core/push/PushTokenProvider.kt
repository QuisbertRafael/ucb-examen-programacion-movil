package edu.ucb.pablostify.core.push

/** Entrega el token FCM del dispositivo (sirve para probar push desde la consola de Firebase). */
interface PushTokenProvider {
    suspend fun getToken(): Result<String>
}

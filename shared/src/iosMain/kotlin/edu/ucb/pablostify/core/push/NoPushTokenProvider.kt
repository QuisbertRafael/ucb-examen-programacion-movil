package edu.ucb.pablostify.core.push

/** iOS: push no esta configurado en este proyecto (se practica solo en Android). */
class NoPushTokenProvider : PushTokenProvider {
    override suspend fun getToken(): Result<String> =
        Result.failure(UnsupportedOperationException("Push no configurado en iOS"))
}

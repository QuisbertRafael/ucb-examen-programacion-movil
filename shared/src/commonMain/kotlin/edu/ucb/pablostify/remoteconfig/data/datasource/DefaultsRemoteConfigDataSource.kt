package edu.ucb.pablostify.remoteconfig.data.datasource

/** Implementacion sin red: siempre devuelve los valores por defecto (la usa iOS). */
class DefaultsRemoteConfigDataSource : RemoteConfigDataSource {
    override suspend fun fetchAndActivate(): Result<Unit> = Result.success(Unit)

    override fun getString(key: String): String = RemoteConfigDefaults.values[key] as? String ?: ""

    override fun getBoolean(key: String): Boolean = RemoteConfigDefaults.values[key] as? Boolean ?: false
}

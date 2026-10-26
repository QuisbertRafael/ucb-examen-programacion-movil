package edu.ucb.pablostify.remoteconfig.data.datasource

interface RemoteConfigDataSource {
    /** Descarga los valores publicados en la consola y los activa. */
    suspend fun fetchAndActivate(): Result<Unit>
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
}

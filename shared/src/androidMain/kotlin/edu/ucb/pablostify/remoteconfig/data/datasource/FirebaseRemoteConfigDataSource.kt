package edu.ucb.pablostify.remoteconfig.data.datasource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import edu.ucb.pablostify.core.firebase.FIREBASE_NOT_CONFIGURED_MESSAGE
import edu.ucb.pablostify.core.firebase.awaitTask

/**
 * Remote Config real de Firebase. Si google-services.json no existe, cae a los valores por defecto
 * (la app sigue funcionando).
 */
class FirebaseRemoteConfigDataSource(private val context: Context) : RemoteConfigDataSource {

    private val remoteConfig: FirebaseRemoteConfig? by lazy {
        if (FirebaseApp.getApps(context).isEmpty()) {
            null
        } else {
            FirebaseRemoteConfig.getInstance().also { config ->
                // 60 s para poder ver cambios rapido en la practica. En produccion usar 3600 (1 hora).
                config.setConfigSettingsAsync(
                    FirebaseRemoteConfigSettings.Builder()
                        .setMinimumFetchIntervalInSeconds(MIN_FETCH_INTERVAL_SECONDS)
                        .build()
                )
                config.setDefaultsAsync(RemoteConfigDefaults.values)
            }
        }
    }

    override suspend fun fetchAndActivate(): Result<Unit> {
        val config = remoteConfig
            ?: return Result.failure(IllegalStateException(FIREBASE_NOT_CONFIGURED_MESSAGE))
        return try {
            config.fetchAndActivate().awaitTask()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getString(key: String): String =
        remoteConfig?.getString(key) ?: (RemoteConfigDefaults.values[key] as? String ?: "")

    override fun getBoolean(key: String): Boolean =
        remoteConfig?.getBoolean(key) ?: (RemoteConfigDefaults.values[key] as? Boolean ?: false)

    private companion object {
        const val MIN_FETCH_INTERVAL_SECONDS = 60L
    }
}

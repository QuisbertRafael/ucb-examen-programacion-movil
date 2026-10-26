package edu.ucb.pablostify.remoteconfig.domain.repository

import edu.ucb.pablostify.remoteconfig.domain.model.AppRemoteConfig
import kotlinx.coroutines.flow.StateFlow

interface RemoteConfigRepository {
    /** Valores actuales. Se actualiza cuando [refresh] termina bien. */
    val config: StateFlow<AppRemoteConfig>
    suspend fun refresh(): Result<AppRemoteConfig>
}

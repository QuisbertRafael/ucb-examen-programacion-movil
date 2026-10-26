package edu.ucb.pablostify.remoteconfig.data.repository

import edu.ucb.pablostify.remoteconfig.data.datasource.RemoteConfigDataSource
import edu.ucb.pablostify.remoteconfig.data.datasource.RemoteConfigKeys
import edu.ucb.pablostify.remoteconfig.domain.model.AppRemoteConfig
import edu.ucb.pablostify.remoteconfig.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RemoteConfigRepositoryImpl(
    private val dataSource: RemoteConfigDataSource
) : RemoteConfigRepository {

    private val _config = MutableStateFlow(read())
    override val config: StateFlow<AppRemoteConfig> = _config.asStateFlow()

    override suspend fun refresh(): Result<AppRemoteConfig> =
        dataSource.fetchAndActivate().map {
            read().also { fresh -> _config.value = fresh }
        }

    private fun read() = AppRemoteConfig(
        welcomeMessage = dataSource.getString(RemoteConfigKeys.WELCOME_MESSAGE),
        notesEnabled = dataSource.getBoolean(RemoteConfigKeys.NOTES_ENABLED),
        maintenanceMode = dataSource.getBoolean(RemoteConfigKeys.MAINTENANCE_MODE)
    )
}

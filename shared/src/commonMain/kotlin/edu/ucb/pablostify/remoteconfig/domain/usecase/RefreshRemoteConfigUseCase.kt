package edu.ucb.pablostify.remoteconfig.domain.usecase

import edu.ucb.pablostify.remoteconfig.domain.model.AppRemoteConfig
import edu.ucb.pablostify.remoteconfig.domain.repository.RemoteConfigRepository

class RefreshRemoteConfigUseCase(private val repository: RemoteConfigRepository) {
    suspend operator fun invoke(): Result<AppRemoteConfig> = repository.refresh()
}

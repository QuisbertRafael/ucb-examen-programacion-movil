package edu.ucb.pablostify.remoteconfig.domain.usecase

import edu.ucb.pablostify.remoteconfig.domain.model.AppRemoteConfig
import edu.ucb.pablostify.remoteconfig.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveRemoteConfigUseCase(private val repository: RemoteConfigRepository) {
    operator fun invoke(): StateFlow<AppRemoteConfig> = repository.config
}

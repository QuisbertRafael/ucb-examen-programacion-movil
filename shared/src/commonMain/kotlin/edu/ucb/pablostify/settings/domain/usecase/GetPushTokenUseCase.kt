package edu.ucb.pablostify.settings.domain.usecase

import edu.ucb.pablostify.core.push.PushTokenProvider

class GetPushTokenUseCase(private val provider: PushTokenProvider) {
    suspend operator fun invoke(): Result<String> = provider.getToken()
}

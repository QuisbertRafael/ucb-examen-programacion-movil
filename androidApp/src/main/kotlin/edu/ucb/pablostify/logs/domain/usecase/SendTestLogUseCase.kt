package edu.ucb.pablostify.logs.domain.usecase

import com.develoop.logs.LogApi
import edu.ucb.pablostify.logs.domain.repository.LogsRepository

class SendTestLogUseCase(private val repository: LogsRepository) {
    suspend operator fun invoke(): Result<LogApi.LogResponse> = repository.sendTestLog()
}

package edu.ucb.pablostify.logs.domain.repository

import com.develoop.logs.LogApi

interface LogsRepository {
    suspend fun sendTestLog(): Result<LogApi.LogResponse>
}

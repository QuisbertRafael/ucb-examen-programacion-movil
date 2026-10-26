package edu.ucb.pablostify.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

actual class LogScheduler actual constructor(
    context: Any?
) {
    private val androidContext = context as? Context
        ?: throw IllegalArgumentException("Context must be an instance of android.content.Context")

    private val logWorkName = "logUploadWork"
    private val intervalMinutes = 15L // minimo permitido por WorkManager en trabajos periodicos

    actual fun schedulePeriodicalUpload() {
        val logRequest = PeriodicWorkRequest.Builder(
            LogUploadWorker::class.java,
            intervalMinutes,
            TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(androidContext.applicationContext).enqueueUniquePeriodicWork(
            logWorkName,
            ExistingPeriodicWorkPolicy.KEEP, // si ya esta programado, no lo duplica
            logRequest
        )

        // Alternativa de una sola ejecucion (para probar sin esperar 15 min):
        // val request = OneTimeWorkRequestBuilder<LogUploadWorker>()
        //     .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
        //     .build()
        // WorkManager.getInstance(androidContext.applicationContext)
        //     .enqueueUniqueWork(logWorkName, ExistingWorkPolicy.KEEP, request)
    }
}

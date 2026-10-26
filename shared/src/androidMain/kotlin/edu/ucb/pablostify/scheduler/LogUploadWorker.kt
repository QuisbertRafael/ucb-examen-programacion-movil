package edu.ucb.pablostify.scheduler

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LogUploadWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters), KoinComponent {

    // La implementacion (gRPC) la registra androidApp en Koin.
    private val logUploader: LogUploader by inject()

    override suspend fun doWork(): Result {
        println("LogUploadWorker: subiendo log por gRPC")
        return try {
            if (logUploader.upload("Log periodico desde Pablostify")) Result.success()
            else Result.retry()
        } catch (e: Exception) {
            println("LogUploadWorker: error ${e.message}")
            Result.retry()
        }
    }
}

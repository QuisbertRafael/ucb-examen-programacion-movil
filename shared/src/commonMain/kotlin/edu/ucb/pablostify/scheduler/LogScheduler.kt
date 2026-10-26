package edu.ucb.pablostify.scheduler

/**
 * Programa la subida periodica de logs en segundo plano.
 * Android: WorkManager. iOS: sin implementacion (stub).
 */
expect class LogScheduler(context: Any?) {
    fun schedulePeriodicalUpload()
}

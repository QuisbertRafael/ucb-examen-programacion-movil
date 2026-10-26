package edu.ucb.pablostify.scheduler

actual class LogScheduler actual constructor(
    context: Any?
) {
    actual fun schedulePeriodicalUpload() {
        // iOS no usa WorkManager (seria BGTaskScheduler). Sin implementacion por ahora.
    }
}

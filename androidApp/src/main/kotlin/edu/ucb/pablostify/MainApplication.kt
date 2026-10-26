package edu.ucb.pablostify

import android.app.Application
import edu.ucb.pablostify.di.initKoinAndroid
import edu.ucb.pablostify.logs.di.logsModule
import edu.ucb.pablostify.scheduler.LogScheduler
import org.koin.android.ext.android.get
import org.koin.core.context.loadKoinModules

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)
        loadKoinModules(logsModule) // implementacion gRPC de LogUploader
        NotificationHelper.createChannel(this)
        get<LogScheduler>().schedulePeriodicalUpload()
    }
}

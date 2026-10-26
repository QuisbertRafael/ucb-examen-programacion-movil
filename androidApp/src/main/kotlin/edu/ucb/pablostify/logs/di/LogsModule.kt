package edu.ucb.pablostify.logs.di

import edu.ucb.pablostify.logs.LogsConfig
import edu.ucb.pablostify.logs.data.GrpcLogUploader
import edu.ucb.pablostify.logs.data.datasource.LogsRemoteDataSource
import edu.ucb.pablostify.scheduler.LogUploader
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val logsModule = module {
    single {
        LogsRemoteDataSource(
            host = LogsConfig.HOST,
            port = LogsConfig.PORT,
            usePlaintext = LogsConfig.USE_PLAINTEXT
        )
    }
    single<LogUploader> { GrpcLogUploader(androidContext(), get()) }
}

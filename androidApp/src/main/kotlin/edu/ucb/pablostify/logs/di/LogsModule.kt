package edu.ucb.pablostify.logs.di

import edu.ucb.pablostify.logs.LogsConfig
import edu.ucb.pablostify.logs.data.GrpcLogUploader
import edu.ucb.pablostify.logs.data.datasource.LogsRemoteDataSource
import edu.ucb.pablostify.logs.data.repository.LogsRepositoryImpl
import edu.ucb.pablostify.logs.domain.repository.LogsRepository
import edu.ucb.pablostify.logs.domain.usecase.SendTestLogUseCase
import edu.ucb.pablostify.logs.presentation.viewmodel.LogsViewModel
import edu.ucb.pablostify.scheduler.LogUploader
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
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
    single<LogsRepository> { LogsRepositoryImpl(get(), androidContext()) }
    single { SendTestLogUseCase(get()) }
    viewModelOf(::LogsViewModel)
}

package edu.ucb.pablostify.di

import edu.ucb.pablostify.core.push.NoPushTokenProvider
import edu.ucb.pablostify.core.push.PushTokenProvider
import edu.ucb.pablostify.core.settings.IosSettingsStorage
import edu.ucb.pablostify.core.settings.SettingsStorage
import edu.ucb.pablostify.notes.data.datasource.InMemoryNotesDataSource
import edu.ucb.pablostify.notes.data.datasource.NotesRemoteDataSource
import edu.ucb.pablostify.remoteconfig.data.datasource.DefaultsRemoteConfigDataSource
import edu.ucb.pablostify.remoteconfig.data.datasource.RemoteConfigDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SettingsStorage> { IosSettingsStorage() }
    single<NotesRemoteDataSource> { InMemoryNotesDataSource() }
    single<RemoteConfigDataSource> { DefaultsRemoteConfigDataSource() }
    single<PushTokenProvider> { NoPushTokenProvider() }
}

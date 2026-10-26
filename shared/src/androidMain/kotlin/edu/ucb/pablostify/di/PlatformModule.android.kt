package edu.ucb.pablostify.di

import edu.ucb.pablostify.core.push.FirebasePushTokenProvider
import edu.ucb.pablostify.core.push.PushTokenProvider
import edu.ucb.pablostify.core.settings.AndroidSettingsStorage
import edu.ucb.pablostify.core.settings.SettingsStorage
import edu.ucb.pablostify.notes.data.datasource.FirebaseNotesDataSource
import edu.ucb.pablostify.notes.data.datasource.NotesRemoteDataSource
import edu.ucb.pablostify.remoteconfig.data.datasource.FirebaseRemoteConfigDataSource
import edu.ucb.pablostify.remoteconfig.data.datasource.RemoteConfigDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SettingsStorage> { AndroidSettingsStorage(androidContext()) }
    single<NotesRemoteDataSource> { FirebaseNotesDataSource(androidContext()) }
    single<RemoteConfigDataSource> { FirebaseRemoteConfigDataSource(androidContext()) }
    single<PushTokenProvider> { FirebasePushTokenProvider(androidContext()) }
}

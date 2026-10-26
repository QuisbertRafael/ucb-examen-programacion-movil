package edu.ucb.pablostify.di

import edu.ucb.pablostify.core.push.FirebasePushTokenProvider
import edu.ucb.pablostify.core.push.PushTokenProvider
import edu.ucb.pablostify.core.settings.AndroidSettingsStorage
import edu.ucb.pablostify.core.settings.SettingsStorage
import edu.ucb.pablostify.notes.data.datasource.FirebaseNotesDataSource
import edu.ucb.pablostify.notes.data.datasource.FirebaseNotesFirestoreDataSource
import edu.ucb.pablostify.notes.data.datasource.NotesRemoteDataSource
import edu.ucb.pablostify.remoteconfig.data.datasource.FirebaseRemoteConfigDataSource
import edu.ucb.pablostify.remoteconfig.data.datasource.RemoteConfigDataSource
import edu.ucb.pablostify.scheduler.LogScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SettingsStorage> { AndroidSettingsStorage(androidContext()) }

    // Opción A: Firebase Realtime Database
    single<NotesRemoteDataSource> { FirebaseNotesDataSource(androidContext()) }

    // Opción B: Cloud Firestore (para cambiar, descomentar esta línea y comentar la Opción A)
    // single<NotesRemoteDataSource> { FirebaseNotesFirestoreDataSource(androidContext()) }

    single<RemoteConfigDataSource> { FirebaseRemoteConfigDataSource(androidContext()) }
    single<PushTokenProvider> { FirebasePushTokenProvider(androidContext()) }
    single { LogScheduler(androidContext()) }
}

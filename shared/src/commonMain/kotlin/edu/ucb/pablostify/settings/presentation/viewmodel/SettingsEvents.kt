package edu.ucb.pablostify.settings.presentation.viewmodel

import edu.ucb.pablostify.core.settings.AppLanguage
import edu.ucb.pablostify.core.settings.ThemeMode

sealed interface SettingsEvents {
    data class OnThemeSelected(val mode: ThemeMode) : SettingsEvents
    data class OnLanguageSelected(val language: AppLanguage) : SettingsEvents
    data object OnRefreshRemoteConfig : SettingsEvents
    data object OnLoadPushToken : SettingsEvents
}

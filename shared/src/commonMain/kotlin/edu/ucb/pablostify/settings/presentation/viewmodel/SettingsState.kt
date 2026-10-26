package edu.ucb.pablostify.settings.presentation.viewmodel

import edu.ucb.pablostify.core.settings.AppLanguage
import edu.ucb.pablostify.core.settings.ThemeMode
import edu.ucb.pablostify.remoteconfig.domain.model.AppRemoteConfig

data class SettingsState(
    val themeMode: ThemeMode,
    val language: AppLanguage,
    val remoteConfig: AppRemoteConfig,
    val isRefreshing: Boolean = false,
    val remoteConfigError: String? = null,
    val isLoadingToken: Boolean = false,
    val pushToken: String? = null,
    val pushError: String? = null
)

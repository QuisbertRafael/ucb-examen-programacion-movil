package edu.ucb.pablostify.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.pablostify.core.settings.AppSettings
import edu.ucb.pablostify.remoteconfig.domain.usecase.ObserveRemoteConfigUseCase
import edu.ucb.pablostify.remoteconfig.domain.usecase.RefreshRemoteConfigUseCase
import edu.ucb.pablostify.settings.domain.usecase.GetPushTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val appSettings: AppSettings,
    private val observeRemoteConfigUseCase: ObserveRemoteConfigUseCase,
    private val refreshRemoteConfigUseCase: RefreshRemoteConfigUseCase,
    private val getPushTokenUseCase: GetPushTokenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsState(
            themeMode = appSettings.themeMode.value,
            language = appSettings.language.value,
            remoteConfig = observeRemoteConfigUseCase().value
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch { appSettings.themeMode.collect { mode -> _state.update { it.copy(themeMode = mode) } } }
        viewModelScope.launch { appSettings.language.collect { lang -> _state.update { it.copy(language = lang) } } }
        viewModelScope.launch { observeRemoteConfigUseCase().collect { cfg -> _state.update { it.copy(remoteConfig = cfg) } } }
    }

    fun emitEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.OnThemeSelected -> appSettings.setThemeMode(event.mode)
            is SettingsEvents.OnLanguageSelected -> appSettings.setLanguage(event.language)
            SettingsEvents.OnRefreshRemoteConfig -> refreshRemoteConfig()
            SettingsEvents.OnLoadPushToken -> loadPushToken()
        }
    }

    private fun refreshRemoteConfig() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, remoteConfigError = null) }
            refreshRemoteConfigUseCase().fold(
                onSuccess = { _state.update { it.copy(isRefreshing = false) } },
                onFailure = { e -> _state.update { it.copy(isRefreshing = false, remoteConfigError = e.message ?: "Error") } }
            )
        }
    }

    private fun loadPushToken() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingToken = true, pushError = null) }
            getPushTokenUseCase().fold(
                onSuccess = { token -> _state.update { it.copy(isLoadingToken = false, pushToken = token) } },
                onFailure = { e -> _state.update { it.copy(isLoadingToken = false, pushError = e.message ?: "Error") } }
            )
        }
    }
}

package edu.ucb.pablostify.core.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Preferencias de usabilidad de la app (tema oscuro/claro e idioma).
 * Es un singleton de Koin: App() observa estos flows y la pantalla de Ajustes los modifica.
 */
class AppSettings(private val storage: SettingsStorage) {

    private val _themeMode = MutableStateFlow(ThemeMode.fromName(storage.getString(KEY_THEME)))
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _language = MutableStateFlow(AppLanguage.fromTag(storage.getString(KEY_LANGUAGE)))
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        storage.putString(KEY_THEME, mode.name)
    }

    fun setLanguage(language: AppLanguage) {
        _language.value = language
        storage.putString(KEY_LANGUAGE, language.tag)
    }

    private companion object {
        const val KEY_THEME = "theme_mode"
        const val KEY_LANGUAGE = "language_tag"
    }
}

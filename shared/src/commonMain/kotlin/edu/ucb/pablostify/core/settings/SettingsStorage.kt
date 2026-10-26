package edu.ucb.pablostify.core.settings

/** Almacenamiento clave/valor simple. Android: SharedPreferences. iOS: NSUserDefaults. */
interface SettingsStorage {
    fun getString(key: String): String?
    fun putString(key: String, value: String)
}

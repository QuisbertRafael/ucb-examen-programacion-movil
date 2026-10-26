package edu.ucb.pablostify.core.settings

import android.content.Context

class AndroidSettingsStorage(context: Context) : SettingsStorage {
    private val prefs = context.getSharedPreferences("pablostify_settings", Context.MODE_PRIVATE)

    override fun getString(key: String): String? = prefs.getString(key, null)

    override fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}

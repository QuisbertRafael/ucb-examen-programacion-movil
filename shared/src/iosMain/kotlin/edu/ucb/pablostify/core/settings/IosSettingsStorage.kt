package edu.ucb.pablostify.core.settings

import platform.Foundation.NSUserDefaults

class IosSettingsStorage : SettingsStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun getString(key: String): String? = defaults.stringForKey(key)

    override fun putString(key: String, value: String) {
        defaults.setObject(value, forKey = key)
    }
}

package edu.ucb.pablostify.core.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages

actual object LocalAppLocale {
    private val default: String = NSLocale.preferredLanguages.first() as String
    private val LocalLocaleTag = staticCompositionLocalOf { default }

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val tag = if (value.isNullOrEmpty()) default else value
        if (value.isNullOrEmpty()) {
            NSUserDefaults.standardUserDefaults.removeObjectForKey("AppleLanguages")
        } else {
            NSUserDefaults.standardUserDefaults.setObject(listOf(tag), "AppleLanguages")
        }
        return LocalLocaleTag.provides(tag)
    }
}

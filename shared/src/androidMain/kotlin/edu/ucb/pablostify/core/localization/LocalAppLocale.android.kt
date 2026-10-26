package edu.ucb.pablostify.core.localization

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale

actual object LocalAppLocale {
    private var systemLocale: Locale? = null

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val configuration = LocalConfiguration.current
        // Guardamos el idioma original del telefono la primera vez, para poder volver a el.
        if (systemLocale == null) systemLocale = Locale.getDefault()
        val locale = if (value.isNullOrEmpty()) systemLocale!! else Locale.forLanguageTag(value)
        Locale.setDefault(locale)
        val newConfiguration = Configuration(configuration).apply { setLocale(locale) }
        return LocalConfiguration.provides(newConfiguration)
    }
}

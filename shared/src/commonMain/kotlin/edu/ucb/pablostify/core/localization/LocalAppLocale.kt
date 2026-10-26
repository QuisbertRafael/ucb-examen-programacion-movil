package edu.ucb.pablostify.core.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue

/**
 * Permite cambiar el idioma de la app en tiempo de ejecucion (sin depender del idioma del telefono).
 * Uso: CompositionLocalProvider(LocalAppLocale provides "en") { ... }   (null o "" = idioma del sistema)
 */
expect object LocalAppLocale {
    @Composable
    infix fun provides(value: String?): ProvidedValue<*>
}

package edu.ucb.pablostify.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import edu.ucb.pablostify.core.settings.ThemeMode

/**
 * Tema de la app. Se aplica UNA sola vez, en App().
 * [themeMode] viene de Ajustes: SYSTEM sigue al telefono, LIGHT/DARK fuerzan el modo.
 */
@Composable
fun PablostifyTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

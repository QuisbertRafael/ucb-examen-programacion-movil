package edu.ucb.pablostify

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import edu.ucb.pablostify.core.localization.LocalAppLocale
import edu.ucb.pablostify.core.settings.AppSettings
import edu.ucb.pablostify.designsystem.theme.PablostifyTheme
import edu.ucb.pablostify.navigation.AppNavHost
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val settings = koinInject<AppSettings>()
    val themeMode by settings.themeMode.collectAsState()
    val language by settings.language.collectAsState()

    // El NavController vive fuera de key(): al cambiar de idioma no se pierde la pantalla actual.
    val navController = rememberNavController()

    CompositionLocalProvider(LocalAppLocale provides language.tag) {
        PablostifyTheme(themeMode = themeMode) {
            // key(language) fuerza a recomponer los textos con el nuevo idioma.
            key(language) {
                AppNavHost(navController)
            }
        }
    }
}

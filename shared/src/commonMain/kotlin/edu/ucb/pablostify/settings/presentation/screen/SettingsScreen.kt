package edu.ucb.pablostify.settings.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.ucb.pablostify.core.settings.AppLanguage
import edu.ucb.pablostify.core.settings.ThemeMode
import edu.ucb.pablostify.designsystem.components.AppCard
import edu.ucb.pablostify.designsystem.components.AppRadioOption
import edu.ucb.pablostify.designsystem.components.AppSecondaryButton
import edu.ucb.pablostify.designsystem.components.ScreenHeader
import edu.ucb.pablostify.designsystem.components.SectionTitle
import edu.ucb.pablostify.designsystem.theme.Spacing
import edu.ucb.pablostify.resources.Res
import edu.ucb.pablostify.resources.common_no
import edu.ucb.pablostify.resources.common_yes
import edu.ucb.pablostify.resources.language_en
import edu.ucb.pablostify.resources.language_es
import edu.ucb.pablostify.resources.language_system
import edu.ucb.pablostify.resources.settings_appearance
import edu.ucb.pablostify.resources.settings_language
import edu.ucb.pablostify.resources.settings_maintenance
import edu.ucb.pablostify.resources.settings_notes_enabled
import edu.ucb.pablostify.resources.settings_push
import edu.ucb.pablostify.resources.settings_push_load
import edu.ucb.pablostify.resources.settings_push_token
import edu.ucb.pablostify.resources.settings_refresh
import edu.ucb.pablostify.resources.settings_remote_config
import edu.ucb.pablostify.resources.settings_title
import edu.ucb.pablostify.resources.settings_welcome_message
import edu.ucb.pablostify.resources.theme_dark
import edu.ucb.pablostify.resources.theme_light
import edu.ucb.pablostify.resources.theme_system
import edu.ucb.pablostify.settings.presentation.viewmodel.SettingsEvents
import edu.ucb.pablostify.settings.presentation.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val config = state.remoteConfig

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.md)
    ) {
        ScreenHeader(title = stringResource(Res.string.settings_title))

        // ---- Usabilidad: modo oscuro
        SectionTitle(stringResource(Res.string.settings_appearance))
        ThemeMode.entries.forEach { mode ->
            AppRadioOption(
                label = stringResource(themeLabel(mode)),
                selected = state.themeMode == mode,
                onSelect = { viewModel.emitEvent(SettingsEvents.OnThemeSelected(mode)) }
            )
        }

        // ---- Localizacion: idioma
        SectionTitle(stringResource(Res.string.settings_language))
        AppLanguage.entries.forEach { language ->
            AppRadioOption(
                label = stringResource(languageLabel(language)),
                selected = state.language == language,
                onSelect = { viewModel.emitEvent(SettingsEvents.OnLanguageSelected(language)) }
            )
        }

        // ---- Remote Config
        SectionTitle(stringResource(Res.string.settings_remote_config))
        AppCard {
            Text(stringResource(Res.string.settings_welcome_message, config.welcomeMessage))
            Text(stringResource(Res.string.settings_notes_enabled, yesNo(config.notesEnabled)))
            Text(stringResource(Res.string.settings_maintenance, yesNo(config.maintenanceMode)))
        }
        AppSecondaryButton(
            text = stringResource(Res.string.settings_refresh),
            onClick = { viewModel.emitEvent(SettingsEvents.OnRefreshRemoteConfig) },
            loading = state.isRefreshing,
            modifier = Modifier.padding(top = Spacing.sm)
        )
        state.remoteConfigError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = Spacing.xs))
        }

        // ---- Notificaciones push
        SectionTitle(stringResource(Res.string.settings_push))
        AppSecondaryButton(
            text = stringResource(Res.string.settings_push_load),
            onClick = { viewModel.emitEvent(SettingsEvents.OnLoadPushToken) },
            loading = state.isLoadingToken
        )
        state.pushToken?.let { token ->
            Text(
                text = stringResource(Res.string.settings_push_token),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = Spacing.sm)
            )
            SelectionContainer { Text(token, style = MaterialTheme.typography.bodySmall) }
        }
        state.pushError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = Spacing.xs))
        }

        Text("", modifier = Modifier.padding(bottom = Spacing.lg))
    }
}

private fun themeLabel(mode: ThemeMode): StringResource = when (mode) {
    ThemeMode.SYSTEM -> Res.string.theme_system
    ThemeMode.LIGHT -> Res.string.theme_light
    ThemeMode.DARK -> Res.string.theme_dark
}

private fun languageLabel(language: AppLanguage): StringResource = when (language) {
    AppLanguage.SYSTEM -> Res.string.language_system
    AppLanguage.SPANISH -> Res.string.language_es
    AppLanguage.ENGLISH -> Res.string.language_en
}

@Composable
private fun yesNo(value: Boolean): String =
    stringResource(if (value) Res.string.common_yes else Res.string.common_no)

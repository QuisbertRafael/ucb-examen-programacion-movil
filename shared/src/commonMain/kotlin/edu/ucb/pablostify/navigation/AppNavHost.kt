package edu.ucb.pablostify.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import edu.ucb.pablostify.designsystem.components.EmptyView
import edu.ucb.pablostify.designsystem.theme.Spacing
import edu.ucb.pablostify.login.presentation.screen.LoginScreen
import edu.ucb.pablostify.moviedetail.presentation.screen.MovieDetailScreen
import edu.ucb.pablostify.notes.presentation.screen.NotesScreen
import edu.ucb.pablostify.profile.presentation.screen.ProfileScreen
import edu.ucb.pablostify.register.presentation.screen.RegisterScreen
import edu.ucb.pablostify.resources.Res
import edu.ucb.pablostify.resources.maintenance_body
import edu.ucb.pablostify.resources.maintenance_title
import edu.ucb.pablostify.resources.nav_home
import edu.ucb.pablostify.resources.nav_notes
import edu.ucb.pablostify.resources.nav_profile
import edu.ucb.pablostify.resources.nav_settings
import edu.ucb.pablostify.resources.notes_disabled
import edu.ucb.pablostify.settings.presentation.screen.SettingsScreen
import edu.ucb.pablostify.shell.presentation.viewmodel.ShellViewModel
import edu.ucb.pablostify.starwars.presentation.screen.StarWarsScreen
import edu.ucb.pablostify.userinformation.presentation.screen.UserInformationScreen
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private enum class BottomTab { HOME, NOTES, PROFILE, SETTINGS }

@Composable
fun AppNavHost(
    navController: NavHostController,
    shellViewModel: ShellViewModel = koinViewModel()
) {
    val shell by shellViewModel.state.collectAsState()
    val config = shell.config

    // Remote Config: si "maintenance_mode" = true en la consola, toda la app muestra esta pantalla.
    if (config.maintenanceMode) {
        MaintenanceView()
        return
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination
    val currentTab = when {
        destination?.hasRoute(NavRoute.MainList::class) == true -> BottomTab.HOME
        destination?.hasRoute(NavRoute.Notes::class) == true -> BottomTab.NOTES
        destination?.hasRoute(NavRoute.Profile::class) == true -> BottomTab.PROFILE
        destination?.hasRoute(NavRoute.Settings::class) == true -> BottomTab.SETTINGS
        else -> null
    }

    Scaffold(
        bottomBar = {
            if (currentTab != null) {
                AppBottomBar(navController, currentTab, config.notesEnabled)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (currentTab == BottomTab.HOME && config.welcomeMessage.isNotBlank()) {
                WelcomeBanner(config.welcomeMessage)
            }
            NavHost(
                navController = navController,
                startDestination = NavRoute.Login,
                modifier = Modifier.weight(1f)
            ) {
                composable<NavRoute.Login> { LoginScreen(navController) }
                composable<NavRoute.Register> { RegisterScreen(navController) }
                //Creacion de la ruta de la pantalla de starwars en el .kt de AppNavHost
                //Se pueden apreciar demás rutas por el hecho de ser un proyecto reciclado
                // EXAMEN: la pantalla nueva reemplaza a StarWarsScreen en esta ruta (MainList = pestana "Inicio").
                composable<NavRoute.MainList> { StarWarsScreen(navController) }

                composable<NavRoute.Notes> {
                    if (config.notesEnabled) {
                        NotesScreen(navController)
                    } else {
                        EmptyView(stringResource(Res.string.notes_disabled))
                    }
                }
                composable<NavRoute.Settings> { SettingsScreen(navController) }

                //Los demas composables son de otras pantallas que no son influyentes en el examen
                composable<NavRoute.MovieDetail> { entry ->
                    val route = entry.toRoute<NavRoute.MovieDetail>()
                    MovieDetailScreen(navController, route.movieId)
                }
                composable<NavRoute.Profile> { ProfileScreen(navController) }
                composable<NavRoute.UserInformation> { UserInformationScreen(navController) }
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    navController: NavHostController,
    current: BottomTab,
    notesEnabled: Boolean
) {
    NavigationBar {
        BottomItem(current == BottomTab.HOME, "\uD83C\uDFE0", Res.string.nav_home) {
            navController.navigateTopLevel(NavRoute.MainList)
        }
        if (notesEnabled) {
            BottomItem(current == BottomTab.NOTES, "\uD83D\uDCDD", Res.string.nav_notes) {
                navController.navigateTopLevel(NavRoute.Notes)
            }
        }
        BottomItem(current == BottomTab.PROFILE, "\uD83D\uDC64", Res.string.nav_profile) {
            navController.navigateTopLevel(NavRoute.Profile)
        }
        BottomItem(current == BottomTab.SETTINGS, "\u2699\uFE0F", Res.string.nav_settings) {
            navController.navigateTopLevel(NavRoute.Settings)
        }
    }
}

@Composable
private fun RowScope.BottomItem(
    selected: Boolean,
    icon: String,
    label: StringResource,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        // El emoji es decorativo: TalkBack solo debe leer la etiqueta.
        icon = { Text(icon, modifier = Modifier.clearAndSetSemantics { }) },
        label = { Text(stringResource(label)) }
    )
}

private fun <T : Any> NavHostController.navigateTopLevel(route: T) {
    navigate(route) {
        popUpTo<NavRoute.MainList> { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
private fun WelcomeBanner(message: String) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(Spacing.sm)
        )
    }
}

@Composable
private fun MaintenanceView() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(Spacing.lg)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(Res.string.maintenance_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(Res.string.maintenance_body),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = Spacing.sm)
                )
            }
        }
    }
}

package edu.ucb.pablostify.profile.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.ucb.pablostify.designsystem.components.AppButton
import edu.ucb.pablostify.designsystem.components.AppCard
import edu.ucb.pablostify.designsystem.components.AppSecondaryButton
import edu.ucb.pablostify.designsystem.components.LoadingView
import edu.ucb.pablostify.designsystem.components.ScreenHeader
import edu.ucb.pablostify.designsystem.theme.Spacing
import edu.ucb.pablostify.navigation.NavRoute
import edu.ucb.pablostify.resources.Res
import edu.ucb.pablostify.resources.profile_favorites
import edu.ucb.pablostify.resources.profile_github
import edu.ucb.pablostify.resources.profile_logout
import edu.ucb.pablostify.resources.profile_see_movies
import edu.ucb.pablostify.resources.profile_title
import org.jetbrains.compose.resources.stringResource
import edu.ucb.pablostify.profile.presentation.viewmodel.ProfileEffects
import edu.ucb.pablostify.profile.presentation.viewmodel.ProfileEvents
import edu.ucb.pablostify.profile.presentation.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                ProfileEffects.NavigateToLogin -> navController.navigate(NavRoute.Login) { launchSingleTop = true }
                ProfileEffects.NavigateToMovies -> navController.navigate(NavRoute.MainList) { launchSingleTop = true }
                ProfileEffects.NavigateToGithubLookup -> navController.navigate(NavRoute.UserInformation)
                is ProfileEffects.ShowMessage -> Unit
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().padding(Spacing.md)) {
        ScreenHeader(title = stringResource(Res.string.profile_title))
        if (state.isLoading) LoadingView()
        state.profile?.let { profile ->
            AppCard {
                Text(profile.user.fullName, style = MaterialTheme.typography.titleMedium)
                Text(profile.user.email)
                Text(stringResource(Res.string.profile_favorites, profile.favoriteMovieIds.size))
                profile.favoriteMovieIds.forEach { Text("\u2022 $it") }
            }
        }
        state.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        AppButton(
            text = stringResource(Res.string.profile_see_movies),
            onClick = { viewModel.emitEvent(ProfileEvents.OpenMovies) },
            modifier = Modifier.padding(top = Spacing.md)
        )
        AppSecondaryButton(
            text = stringResource(Res.string.profile_github),
            onClick = { viewModel.emitEvent(ProfileEvents.OpenGithubLookup) },
            modifier = Modifier.padding(top = Spacing.sm)
        )
        AppSecondaryButton(
            text = stringResource(Res.string.profile_logout),
            onClick = { viewModel.emitEvent(ProfileEvents.Logout) },
            modifier = Modifier.padding(top = Spacing.sm)
        )
    }
}

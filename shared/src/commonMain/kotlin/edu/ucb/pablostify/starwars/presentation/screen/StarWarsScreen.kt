package edu.ucb.pablostify.starwars.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.ucb.pablostify.designsystem.components.AppCard
import edu.ucb.pablostify.designsystem.components.ErrorView
import edu.ucb.pablostify.designsystem.components.LoadingView
import edu.ucb.pablostify.designsystem.theme.Spacing
import edu.ucb.pablostify.resources.Res
import edu.ucb.pablostify.resources.common_retry
import edu.ucb.pablostify.resources.starwars_eyes
import edu.ucb.pablostify.resources.starwars_gender
import edu.ucb.pablostify.resources.starwars_hair
import edu.ucb.pablostify.resources.starwars_height
import edu.ucb.pablostify.resources.starwars_mass
import edu.ucb.pablostify.resources.starwars_name
import edu.ucb.pablostify.resources.starwars_skin
import edu.ucb.pablostify.starwars.presentation.viewmodel.StarWarsEffect
import edu.ucb.pablostify.starwars.presentation.viewmodel.StarWarsEvent
import edu.ucb.pablostify.starwars.presentation.viewmodel.StarWarsViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StarWarsScreen(navController: NavHostController, viewModel: StarWarsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StarWarsEffect.ShowToast -> Unit
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = Spacing.md)) {
        if (state.isLoading) LoadingView()
        state.error?.let { message ->
            ErrorView(
                message = message,
                retryLabel = stringResource(Res.string.common_retry),
                onRetry = { viewModel.emitEvent(StarWarsEvent.Retry) }
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            contentPadding = PaddingValues(vertical = Spacing.md)
        ) {
            items(state.characters) { character ->
                AppCard {
                    Text(stringResource(Res.string.starwars_name, character.name))
                    Text(stringResource(Res.string.starwars_height, character.height))
                    Text(stringResource(Res.string.starwars_mass, character.mass))
                    Text(stringResource(Res.string.starwars_hair, character.hairColor))
                    Text(stringResource(Res.string.starwars_skin, character.skinColor))
                    Text(stringResource(Res.string.starwars_eyes, character.eyeColor))
                    Text(stringResource(Res.string.starwars_gender, character.gender))
                }
            }
        }
    }
}

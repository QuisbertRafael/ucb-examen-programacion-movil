package edu.ucb.pablostify.notes.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.ucb.pablostify.designsystem.components.AppButton
import edu.ucb.pablostify.designsystem.components.AppCard
import edu.ucb.pablostify.designsystem.components.AppTextField
import edu.ucb.pablostify.designsystem.components.EmptyView
import edu.ucb.pablostify.designsystem.components.ErrorView
import edu.ucb.pablostify.designsystem.components.LoadingView
import edu.ucb.pablostify.designsystem.components.ScreenHeader
import edu.ucb.pablostify.designsystem.theme.Spacing
import edu.ucb.pablostify.notes.presentation.viewmodel.NotesEvents
import edu.ucb.pablostify.notes.presentation.viewmodel.NotesViewModel
import edu.ucb.pablostify.resources.Res
import edu.ucb.pablostify.resources.common_retry
import edu.ucb.pablostify.resources.notes_add
import edu.ucb.pablostify.resources.notes_delete
import edu.ucb.pablostify.resources.notes_empty
import edu.ucb.pablostify.resources.notes_hint
import edu.ucb.pablostify.resources.notes_subtitle
import edu.ucb.pablostify.resources.notes_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = Spacing.md)) {
        ScreenHeader(
            title = stringResource(Res.string.notes_title),
            subtitle = stringResource(Res.string.notes_subtitle)
        )
        AppTextField(
            value = state.draft,
            onValueChange = { viewModel.emitEvent(NotesEvents.OnDraftChanged(it)) },
            label = stringResource(Res.string.notes_hint)
        )
        AppButton(
            text = stringResource(Res.string.notes_add),
            onClick = { viewModel.emitEvent(NotesEvents.OnAdd) },
            enabled = state.draft.isNotBlank(),
            modifier = Modifier.padding(top = Spacing.sm)
        )

        state.error?.let { message ->
            ErrorView(
                message = message,
                retryLabel = stringResource(Res.string.common_retry),
                onRetry = { viewModel.emitEvent(NotesEvents.OnRetry) }
            )
        }

        when {
            state.isLoading -> LoadingView()
            state.notes.isEmpty() && state.error == null -> EmptyView(stringResource(Res.string.notes_empty))
            else -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                contentPadding = PaddingValues(vertical = Spacing.md)
            ) {
                items(state.notes, key = { it.id }) { note ->
                    AppCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = note.text, modifier = Modifier.weight(1f))
                            TextButton(onClick = { viewModel.emitEvent(NotesEvents.OnDelete(note.id)) }) {
                                Text(stringResource(Res.string.notes_delete))
                            }
                        }
                    }
                }
            }
        }
    }
}

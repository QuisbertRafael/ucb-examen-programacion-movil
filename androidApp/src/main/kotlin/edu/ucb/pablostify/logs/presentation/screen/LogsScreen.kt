package edu.ucb.pablostify.logs.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.ucb.pablostify.designsystem.components.AppButton
import edu.ucb.pablostify.designsystem.components.AppCard
import edu.ucb.pablostify.designsystem.components.LoadingView
import edu.ucb.pablostify.designsystem.components.ScreenHeader
import edu.ucb.pablostify.designsystem.theme.Spacing
import edu.ucb.pablostify.logs.presentation.viewmodel.LogsEvent
import edu.ucb.pablostify.logs.presentation.viewmodel.LogsViewModel
import edu.ucb.pablostify.resources.Res
import edu.ucb.pablostify.resources.logs_error_title
import edu.ucb.pablostify.resources.logs_result_code
import edu.ucb.pablostify.resources.logs_send_button
import edu.ucb.pablostify.resources.logs_subtitle
import edu.ucb.pablostify.resources.logs_success_title
import edu.ucb.pablostify.resources.logs_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LogsScreen(
    navController: NavHostController,
    viewModel: LogsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        ScreenHeader(
            title = stringResource(Res.string.logs_title),
            subtitle = stringResource(Res.string.logs_subtitle)
        )

        AppButton(
            text = stringResource(Res.string.logs_send_button),
            onClick = { viewModel.emitEvent(LogsEvent.SendTestLog) },
            loading = state.isLoading
        )

        if (state.isLoading) {
            LoadingView()
        }

        state.resultCode?.let { resultCode ->
            AppCard {
                Text(
                    text = stringResource(Res.string.logs_success_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(Res.string.logs_result_code, resultCode),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        state.error?.let { errorMsg ->
            AppCard {
                Text(
                    text = stringResource(Res.string.logs_error_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = errorMsg,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

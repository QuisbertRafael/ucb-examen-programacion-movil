package edu.ucb.pablostify.login.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.ucb.pablostify.login.presentation.viewmodel.LoginEffects
import edu.ucb.pablostify.login.presentation.viewmodel.LoginEvents
import edu.ucb.pablostify.login.presentation.viewmodel.LoginViewModel
import edu.ucb.pablostify.designsystem.components.AppButton
import edu.ucb.pablostify.designsystem.components.AppTextField
import edu.ucb.pablostify.designsystem.theme.Spacing
import edu.ucb.pablostify.navigation.NavRoute
import edu.ucb.pablostify.resources.Res
import edu.ucb.pablostify.resources.login_app_name
import edu.ucb.pablostify.resources.login_notice_any
import edu.ucb.pablostify.resources.login_notice_early
import edu.ucb.pablostify.resources.login_password
import edu.ucb.pablostify.resources.login_register
import edu.ucb.pablostify.resources.login_submit
import edu.ucb.pablostify.resources.login_title
import edu.ucb.pablostify.resources.login_username
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LoginEffects.NavigateToMovieList -> navController.navigate(NavRoute.MainList) { launchSingleTop = true }
                LoginEffects.NavigateToRegister -> navController.navigate(NavRoute.Register)
                is LoginEffects.ShowMessage -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(Spacing.lg),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(Res.string.login_app_name), style = MaterialTheme.typography.headlineMedium)
        Text(stringResource(Res.string.login_notice_any), style = MaterialTheme.typography.bodySmall)
        Text(stringResource(Res.string.login_notice_early), style = MaterialTheme.typography.bodySmall)
        Text(
            stringResource(Res.string.login_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = Spacing.md)
        )
        AppTextField(
            value = state.username,
            onValueChange = { viewModel.emitEvent(LoginEvents.OnUsernameChanged(it)) },
            label = stringResource(Res.string.login_username)
        )
        AppTextField(
            value = state.password,
            onValueChange = { viewModel.emitEvent(LoginEvents.OnPasswordChanged(it)) },
            label = stringResource(Res.string.login_password),
            isPassword = true,
            modifier = Modifier.padding(top = Spacing.sm)
        )
        state.errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = Spacing.sm))
        }
        AppButton(
            text = stringResource(Res.string.login_submit),
            onClick = { viewModel.emitEvent(LoginEvents.OnSubmit) },
            loading = state.isLoading,
            modifier = Modifier.padding(top = Spacing.md)
        )
        TextButton(onClick = { viewModel.emitEvent(LoginEvents.OnRegister) }) {
            Text(stringResource(Res.string.login_register))
        }
    }
}

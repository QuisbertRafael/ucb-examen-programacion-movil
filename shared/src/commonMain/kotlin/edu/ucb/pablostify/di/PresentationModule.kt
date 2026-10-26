package edu.ucb.pablostify.di

import edu.ucb.pablostify.login.presentation.viewmodel.LoginViewModel
import edu.ucb.pablostify.moviedetail.presentation.viewmodel.MovieDetailViewModel
import edu.ucb.pablostify.profile.presentation.viewmodel.ProfileViewModel
import edu.ucb.pablostify.register.presentation.viewmodel.RegisterViewModel
import edu.ucb.pablostify.userinformation.presentation.viewmodel.UserInformationViewModel
import org.koin.core.module.dsl.viewModelOf
import edu.ucb.pablostify.notes.presentation.viewmodel.NotesViewModel
import edu.ucb.pablostify.settings.presentation.viewmodel.SettingsViewModel
import edu.ucb.pablostify.shell.presentation.viewmodel.ShellViewModel
import org.koin.dsl.module
import edu.ucb.pablostify.starwars.presentation.viewmodel.StarWarsViewModel


val presentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)

    viewModelOf(::MovieDetailViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::UserInformationViewModel)
    viewModelOf(::StarWarsViewModel)
    viewModelOf(::NotesViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ShellViewModel)


}

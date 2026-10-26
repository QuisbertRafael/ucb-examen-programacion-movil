package edu.ucb.pablostify.di

import edu.ucb.pablostify.login.domain.usecase.LoginUseCase
import edu.ucb.pablostify.moviedetail.domain.usecase.GetMovieDetailUseCase
import edu.ucb.pablostify.moviedetail.domain.usecase.SubmitReviewUseCase
// TODO (examen): importar aquí el UseCase de la pantalla nueva
import edu.ucb.pablostify.profile.domain.usecase.GetProfileUseCase
import edu.ucb.pablostify.profile.domain.usecase.LogoutUseCase
import edu.ucb.pablostify.register.domain.usecase.RegisterUseCase
import edu.ucb.pablostify.userinformation.domain.usecase.FindAliasUseCase
import org.koin.core.module.dsl.singleOf
import edu.ucb.pablostify.notes.domain.usecase.AddNoteUseCase
import edu.ucb.pablostify.notes.domain.usecase.DeleteNoteUseCase
import edu.ucb.pablostify.notes.domain.usecase.ObserveNotesUseCase
import edu.ucb.pablostify.remoteconfig.domain.usecase.ObserveRemoteConfigUseCase
import edu.ucb.pablostify.remoteconfig.domain.usecase.RefreshRemoteConfigUseCase
import edu.ucb.pablostify.settings.domain.usecase.GetPushTokenUseCase
import org.koin.dsl.module
import edu.ucb.pablostify.starwars.domain.usecase.GetStarWarsCharactersUseCase

val domainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::GetStarWarsCharactersUseCase)
    singleOf(::GetMovieDetailUseCase)
    singleOf(::SubmitReviewUseCase)
    singleOf(::GetProfileUseCase)
    singleOf(::LogoutUseCase)
    singleOf(::FindAliasUseCase)
    singleOf(::ObserveNotesUseCase)
    singleOf(::AddNoteUseCase)
    singleOf(::DeleteNoteUseCase)
    singleOf(::ObserveRemoteConfigUseCase)
    singleOf(::RefreshRemoteConfigUseCase)
    singleOf(::GetPushTokenUseCase)


}

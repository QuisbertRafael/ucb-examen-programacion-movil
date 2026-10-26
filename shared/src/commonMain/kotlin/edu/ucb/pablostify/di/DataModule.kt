package edu.ucb.pablostify.di

import edu.ucb.pablostify.login.data.LoginRepositoryImpl
import edu.ucb.pablostify.login.domain.repository.LoginRepository
import edu.ucb.pablostify.moviedetail.data.MovieDetailRepositoryImpl
import edu.ucb.pablostify.moviedetail.domain.repository.MovieDetailRepository
import edu.ucb.pablostify.profile.data.ProfileRepositoryImpl
import edu.ucb.pablostify.profile.domain.repository.ProfileRepository
import edu.ucb.pablostify.register.data.RegisterRepositoryImpl
import edu.ucb.pablostify.register.domain.repository.RegisterRepository
import edu.ucb.pablostify.userinformation.data.datasource.GithubRemoteDataSource
import edu.ucb.pablostify.userinformation.data.repository.GithubRepositoryImpl
import edu.ucb.pablostify.userinformation.data.service.GitHubApiService
import edu.ucb.pablostify.userinformation.domain.repository.GithubRepository
import edu.ucb.pablostify.core.settings.AppSettings
import edu.ucb.pablostify.notes.data.repository.NotesRepositoryImpl
import edu.ucb.pablostify.notes.domain.repository.NotesRepository
import edu.ucb.pablostify.remoteconfig.data.repository.RemoteConfigRepositoryImpl
import edu.ucb.pablostify.remoteconfig.domain.repository.RemoteConfigRepository
import org.koin.dsl.module

//Aqui se agreagron los import de starwars ya que previamente se eliminaron los de movielist
import edu.ucb.pablostify.starwars.data.datasource.StarWarsRemoteDataSource
import edu.ucb.pablostify.starwars.data.repository.StarWarsRepositoryImpl
import edu.ucb.pablostify.starwars.data.service.StarWarsService
import edu.ucb.pablostify.starwars.domain.repository.StarWarsRepository

val dataModule = module {
    single<LoginRepository> { LoginRepositoryImpl(getOrNull()) }
    single<RegisterRepository> { RegisterRepositoryImpl() }
    single<MovieDetailRepository> { MovieDetailRepositoryImpl() }
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<GithubRemoteDataSource> { GitHubApiService() }
    single<GithubRepository> { GithubRepositoryImpl(get()) }

    //Aqui se agreagron los single de starwars ya que previamente se eliminaron los de movielist
    single<StarWarsRemoteDataSource> { StarWarsService() }
    single<StarWarsRepository> { StarWarsRepositoryImpl(get()) }

    // Firebase / usabilidad (las fuentes de datos vienen de platformModule())
    single { AppSettings(get()) }
    single<RemoteConfigRepository> { RemoteConfigRepositoryImpl(get()) }
    single<NotesRepository> { NotesRepositoryImpl(get(), getOrNull()) }

}

package edu.ucb.pablostify.starwars.presentation.viewmodel

sealed interface StarWarsEvent {
    data object Retry : StarWarsEvent
}

package edu.ucb.pablostify.starwars.presentation.viewmodel

sealed interface StarWarsEffect {
    data class ShowToast(val message: String) : StarWarsEffect
}

package edu.ucb.pablostify.starwars.presentation.viewmodel

import edu.ucb.pablostify.starwars.domain.model.Character

data class StarWarsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val characters: List<Character> = emptyList()
)

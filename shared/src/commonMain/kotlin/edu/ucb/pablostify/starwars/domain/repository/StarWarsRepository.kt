package edu.ucb.pablostify.starwars.domain.repository

import edu.ucb.pablostify.starwars.domain.model.Character

interface StarWarsRepository {
    suspend fun getCharacters(): Result<List<Character>>
}
